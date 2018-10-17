package com.hfutonline.mly.modules.api.controller;

import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.api.auth.ApiRealm;
import com.hfutonline.mly.modules.api.domain.ApiGroup;
import com.hfutonline.mly.modules.api.domain.ApiQuery;
import com.hfutonline.mly.modules.api.filter.ApiFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenliangliang
 * @date 2018/10/17
 */
@RestController
@RequestMapping("/control")
public class ControlController {

    private ApiRealm apiRealm;


    @PostMapping("/authToken")
    public Result setAuthToken(@Validated(value = {ApiGroup.SetAuthToken.class}) ApiQuery query) {
        return doSet(Type.AUTH_TOKEN, query.getToken(), query.getEnable(), ApiFilter::setAuthToken);
    }

    @PostMapping("/cross")
    public Result setCross(@Validated(ApiGroup.SetCross.class) ApiQuery query) {
        return doSet(Type.CROSS, query.getToken(), query.getEnable(), ApiFilter::setCross);
    }

    private Result doSet(Type type, String token, String enable, Set set) {
        boolean setValue;
        if (StringUtils.equalsIgnoreCase(enable, "true")) {
            setValue = true;
        } else if (StringUtils.equalsIgnoreCase(enable, "false")) {
            setValue = false;
        } else {
            return Result.error(HttpStatus.BAD_REQUEST, "enable参数值错误,只能是true或false");
        }
        Integer appId = apiRealm.doAuthentication(token);
        if (appId == null) {
            return Result.error(HttpStatus.UNAUTHORIZED, "token 验证未通过");
        }
        set.doSet(setValue);
        String msg = setValue ? "已开启" + type.getDesc() : "已关闭" + type.getDesc();
        return Result.OK(msg);
    }

    @FunctionalInterface
    interface Set {
        /**
         * 设置开关
         * @param enable 开关
         */
        void doSet(boolean enable);
    }

    private enum Type {
        AUTH_TOKEN("token验证"),
        CROSS("跨域请求");

        String desc;

        Type(String desc) {
            this.desc = desc;
        }

        String getDesc() {
            return this.desc;
        }
    }


    public ControlController(ApiRealm apiRealm) {
        this.apiRealm = apiRealm;
    }
}
