package com.hfutonline.mly.modules.web.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hfutonline.mly.modules.web.entity.AppServer;
import com.hfutonline.mly.modules.web.service.AppServerService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;


/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@RestController
@RequestMapping("web/appserver")
public class AppServerController {

    private AppServerService appServerService;

    @Autowired
    protected  AppServerController(AppServerService appServerService) {
        this.appServerService = appServerService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("web:appserver:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = appServerService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("web:appserver:info")
    public Result info(@PathVariable("id") Integer id) {
            AppServer appServer = appServerService.selectById(id);

        return Result.OK().put("appServer", appServer);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("web:appserver:save")
    public Result save(@RequestBody AppServer appServer) {
            appServerService.insert(appServer);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("web:appserver:update")
    public Result update(@RequestBody AppServer appServer) {
            appServerService.updateById(appServer);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("web:appserver:delete")
    public Result delete(@RequestBody Integer[]ids) {
            appServerService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
