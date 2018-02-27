package com.hfutonline.mly.modules.news.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hfutonline.mly.modules.news.entity.CatalogTag;
import com.hfutonline.mly.modules.news.service.CatalogTagService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;


/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:20:50
 */
@RestController
@RequestMapping("news/catalogtag")
public class CatalogTagController {

    private CatalogTagService catalogTagService;

    @Autowired
    protected  CatalogTagController(CatalogTagService catalogTagService) {
        this.catalogTagService = catalogTagService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:catalogtag:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = catalogTagService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("news:catalogtag:info")
    public Result info(@PathVariable("id") Integer id) {
            CatalogTag catalogTag = catalogTagService.selectById(id);

        return Result.OK().put("catalogTag", catalogTag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:catalogtag:save")
    public Result save(@RequestBody CatalogTag catalogTag) {
            catalogTagService.insert(catalogTag);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:catalogtag:update")
    public Result update(@RequestBody CatalogTag catalogTag) {
            catalogTagService.updateById(catalogTag);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:catalogtag:delete")
    public Result delete(@RequestBody Integer[]ids) {
            catalogTagService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
