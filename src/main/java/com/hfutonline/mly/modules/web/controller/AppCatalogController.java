package com.hfutonline.mly.modules.web.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hfutonline.mly.modules.web.entity.AppCatalog;
import com.hfutonline.mly.modules.web.service.AppCatalogService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;


/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@RestController
@RequestMapping("web/appcatalog")
public class AppCatalogController {

    private AppCatalogService appCatalogService;

    @Autowired
    protected  AppCatalogController(AppCatalogService appCatalogService) {
        this.appCatalogService = appCatalogService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("web:appcatalog:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = appCatalogService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("web:appcatalog:info")
    public Result info(@PathVariable("id") Integer id) {
            AppCatalog appCatalog = appCatalogService.selectById(id);

        return Result.OK().put("appCatalog", appCatalog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("web:appcatalog:save")
    public Result save(@RequestBody AppCatalog appCatalog) {
            appCatalogService.insert(appCatalog);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("web:appcatalog:update")
    public Result update(@RequestBody AppCatalog appCatalog) {
            appCatalogService.updateById(appCatalog);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("web:appcatalog:delete")
    public Result delete(@RequestBody Integer[]ids) {
            appCatalogService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
