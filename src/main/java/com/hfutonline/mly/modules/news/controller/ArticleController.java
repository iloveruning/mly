package com.hfutonline.mly.modules.news.controller;

import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.service.ArticleService;
import com.hfutonline.mly.modules.news.vo.ArticleVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 文章管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
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
        System.out.println(params);
        PageInfo page = articleService.queryPage(params);

        return Result.OK().put("page", page);
    }


    @GetMapping("/info/{id}")
    @RequiresPermissions("news:article:info")
    public Result detail(@PathVariable("id") Long id){

        ArticleVo article=articleService.getDetail(id);
        return Result.OK().put("article",article);
    }


    /**
     * 审核文章
     */
    @SysLog("审核文章")
    @GetMapping("/audit/{id}")
    @RequiresPermissions("news:article:audit")
    public Result audit(@PathVariable("id") Long id){

        return Result.error("您无审核权限");
    }


    /**
     * 信息
     */
//    @GetMapping("/info/{id}")
//    @RequiresPermissions("news:article:info")
//    public Result info(@PathVariable("id") Long id) {
//            Article article = articleService.selectById(id);
//
//        return Result.OK().put("article", article);
//    }

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
    @SysLog("修改文章")
    @PostMapping("/update")
    @RequiresPermissions("news:article:update")
    public Result update(@RequestBody Article article) {
            articleService.updateById(article);

        return Result.OK();
    }

    /**
     * 删除
     */
    @SysLog("删除文章")
    @PostMapping("/delete")
    @RequiresPermissions("news:article:delete")
    public Result delete(@RequestBody Long[]ids) {
            articleService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
