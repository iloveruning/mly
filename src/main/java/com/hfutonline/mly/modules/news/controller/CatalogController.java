package com.hfutonline.mly.modules.news.controller;

import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.news.entity.Catalog;
import com.hfutonline.mly.modules.news.service.CatalogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 栏目
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@RestController
@RequestMapping("news/catalog")
public class CatalogController {

    private CatalogService catalogService;

    @Autowired
    protected  CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:catalog:list")
    public List<Catalog> list() {

        List<Catalog> catalogList=catalogService.selectList(null);

        return catalogList;
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("news:catalog:info")
    public Result info(@PathVariable("id") Integer id) {
            Catalog catalog = catalogService.selectById(id);

        return Result.OK().put("catalog", catalog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:catalog:save")
    public Result save(@RequestBody Catalog catalog) {
            catalogService.insert(catalog);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:catalog:update")
    public Result update(@RequestBody Catalog catalog) {
            catalogService.updateById(catalog);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:catalog:delete")
    public Result delete(@RequestBody Integer[]ids) {
            catalogService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
