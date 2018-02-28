package com.hfutonline.mly.modules.web.controller;

import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.validator.ValidatorUtils;
import com.hfutonline.mly.common.validator.group.Add;
import com.hfutonline.mly.common.validator.group.Update;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import com.hfutonline.mly.modules.web.entity.App;
import com.hfutonline.mly.modules.web.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 应用表
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@Slf4j
@RestController
@RequestMapping("web/app")
public class AppController {

    private AppService appService;

    @Autowired
    protected AppController(AppService appService) {
        this.appService = appService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("web:app:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = appService.queryPage(ShiroKit.getPrincipal(),params);
        return Result.OK().put("page", page);
    }

    /**
     * 注册应用
     */
    @PostMapping("/register")
    @RequiresPermissions("web:app:save")
    public Result register(@RequestBody App app) {

        try {
            ValidatorUtils.validateEntity(app, Add.class);
            app.setUsername(ShiroKit.getUserName());
            appService.register(app);
            return Result.OK();
        } catch (ParamsException e) {
            return Result.error(HttpStatus.BAD_REQUEST, e.getMsg());
        } catch (TransactionException ee) {
            return Result.error(ee.getMessage());
        }
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("web:app:info")
    public Result info(@PathVariable("id") Integer id) {
        App app = appService.selectById(id);
        //获取用户所属的角色列表
        List<Integer> serverIdList = appService.getAppServerIds(id);
        app.setServerIdList(serverIdList);
        return Result.OK().put("app", app);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("web:app:update")
    public Result update(@RequestBody App app) {
        try {
            ValidatorUtils.validateEntity(app, Update.class);
            appService.update(app);
            return Result.OK();
        } catch (ParamsException e) {
            return Result.error(HttpStatus.BAD_REQUEST, e.getMsg());
        } catch (TransactionException ee) {
            return Result.error(ee.getMessage());
        }
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("web:app:delete")
    public Result delete(@RequestBody Integer[] ids) {
        appService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
