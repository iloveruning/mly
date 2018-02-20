package com.hfutonline.mly.modules.news.controller;

import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.news.entity.Tag;
import com.hfutonline.mly.modules.news.service.TagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author chenliangliang
 * @email chenliangliang68@163.com
 * @date 2018-02-20 22:56:06
 */
@RestController
@RequestMapping("news/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    protected  TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:tag:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = tagService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("news:tag:info")
    public Result info(@PathVariable("id") Integer id) {
            Tag tag = tagService.selectById(id);

        return Result.OK().put("tag", tag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:tag:save")
    public Result save(@RequestBody Tag tag) {
            tagService.insert(tag);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:tag:update")
    public Result update(@RequestBody Tag tag) {
            tagService.updateById(tag);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:tag:delete")
    public Result delete(@RequestBody Integer[]ids) {
            tagService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
