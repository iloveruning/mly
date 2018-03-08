package com.hfutonline.mly.modules.web.controller;

import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.exception.MlyException;
import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.EhcacheTemplate;
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

    private EhcacheTemplate cacheTemplate;

    private final String cacheName = "app";

    private final String prefix = "app_";

    @Autowired
    protected AppController(AppService appService, EhcacheTemplate cacheTemplate) {
        this.appService = appService;
        this.cacheTemplate = cacheTemplate;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("web:app:list")
    public Result list(@RequestParam Map<String, Object> params) {
        return cacheTemplate.cacheable(cacheName, prefix + "list_" + params.toString(), () -> {
            PageInfo page = appService.queryPage(ShiroKit.getPrincipal(), params);
            return Result.OK().put("page", page);
        });

    }

    /**
     * 注册应用
     */
    @SysLog("注册应用")
    @PostMapping("/register")
    @RequiresPermissions("web:app:save")
    public Result register(@RequestBody App app) {

        try {
            ValidatorUtils.validateEntity(app, Add.class);
            app.setUsername(ShiroKit.getUserName());
            appService.register(app);
            cacheTemplate.clear(cacheName);
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
    @SysLog("修改应用信息")
    @PostMapping("/update")
    @RequiresPermissions("web:app:update")
    public Result update(@RequestBody App app) {
        try {
            ValidatorUtils.validateEntity(app, Update.class);
            appService.update(app);
            cacheTemplate.clear(cacheName);
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
    @SysLog("删除应用")
    @PostMapping("/delete")
    @RequiresPermissions("web:app:delete")
    public Result delete(@RequestBody Integer[] ids) {

        if (ids.length > 0) {
            try {
                appService.delete(ids);
                cacheTemplate.clear(cacheName);
            } catch (MlyException e) {
                return Result.error(e.getMsg());
            }
        }

        return Result.OK();
    }

}
