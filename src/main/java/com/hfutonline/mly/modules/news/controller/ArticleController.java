package com.hfutonline.mly.modules.news.controller;

import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.service.ArticleService;
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
@RequestMapping("news/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    protected  ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:article:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = articleService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("news:article:info")
    public Result info(@PathVariable("id") Long id) {
            Article article = articleService.selectById(id);

        return Result.OK().put("article", article);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:article:save")
    public Result save(@RequestBody Article article) {
            articleService.insert(article);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:article:update")
    public Result update(@RequestBody Article article) {
            articleService.updateById(article);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:article:delete")
    public Result delete(@RequestBody Long[]ids) {
            articleService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
