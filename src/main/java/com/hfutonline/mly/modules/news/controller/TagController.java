package com.hfutonline.mly.modules.news.controller;

import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.validator.ValidatorUtils;
import com.hfutonline.mly.modules.news.entity.Tag;
import com.hfutonline.mly.modules.news.service.TagService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 标签管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:41:59
 */
@RestController
@RequestMapping("news/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    protected TagController(TagService tagService) {
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

        try {
            ValidatorUtils.validateEntity(tag);
            tag.setUsername(ShiroKit.getUserName());
            tagService.insert(tag);
        } catch (ParamsException e) {
            return Result.error(e.getMsg());
        }
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:tag:update")
    public Result update(@RequestBody Tag tag) {

        try {
            ValidatorUtils.validateEntity(tag);
            tagService.updateById(tag);
        } catch (ParamsException e) {
            return Result.error(e.getMsg());
        }

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:tag:delete")
    public Result delete(@RequestBody Integer[] ids) {
        tagService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
