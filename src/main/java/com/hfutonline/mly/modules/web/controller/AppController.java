package com.hfutonline.mly.modules.web.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hfutonline.mly.modules.web.entity.App;
import com.hfutonline.mly.modules.web.service.AppService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;


/**
 * 应用表
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@RestController
@RequestMapping("web/app")
public class AppController {

    private AppService appService;

    @Autowired
    protected  AppController(AppService appService) {
        this.appService = appService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("web:app:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = appService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("web:app:info")
    public Result info(@PathVariable("id") Integer id) {
            App app = appService.selectById(id);

        return Result.OK().put("app", app);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("web:app:save")
    public Result save(@RequestBody App app) {
            appService.insert(app);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("web:app:update")
    public Result update(@RequestBody App app) {
            appService.updateById(app);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("web:app:delete")
    public Result delete(@RequestBody Integer[]ids) {
            appService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
