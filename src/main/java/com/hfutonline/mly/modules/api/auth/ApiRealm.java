package com.hfutonline.mly.modules.api.auth;

import com.auth0.jwt.interfaces.Claim;
import com.hfutonline.mly.common.utils.EhcacheTemplate;
import com.hfutonline.mly.common.validator.JwtUtil;
import com.hfutonline.mly.modules.web.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
@Service
public class ApiRealm implements Realm {

    private AppService appService;

    private EhcacheTemplate cacheTemplate;

    public static String cacheName = "api";

    public static String prefix = "apiInfo";

    @Autowired
    protected ApiRealm(AppService appService, EhcacheTemplate cacheTemplate) {
        this.appService = appService;
        this.cacheTemplate = cacheTemplate;
    }


    @Override
    public Integer doAuthentication(String token) {

        String jwtToken = JwtUtil.TOKEN_HEADER + "." + token;
        Map<String, Claim> claims = JwtUtil.getClaimsFromToken(jwtToken);
        if (claims == null) {
            return null;
        }

        try {
            Integer appId = Integer.valueOf(claims.get("id").asString());
            if (appId > 0) {
                return appId;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ApiInfo doAuthorization(Integer appId) {

        return cacheTemplate.cacheable(cacheName, prefix + appId, () -> {
            List<Integer> catalogIdList = appService.getAppCatalogIdList(appId);
            return new ApiInfo(appId, catalogIdList);
        });
    }


}
