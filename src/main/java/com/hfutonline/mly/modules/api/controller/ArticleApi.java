package com.hfutonline.mly.modules.api.controller;

import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.validator.ValidatorUtils;
import com.hfutonline.mly.common.validator.group.Add;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.service.ArticleService;
import com.hfutonline.mly.modules.news.service.CatalogService;
import com.hfutonline.mly.modules.news.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
@RestController
@RequestMapping("/open/api")
public class ArticleApi {

    private ArticleService articleService;
    private CatalogService catalogService;
    private TagService tagService;

    @Autowired
    protected ArticleApi(ArticleService articleService,CatalogService catalogService,
                         TagService tagService) {
        this.articleService = articleService;
        this.catalogService=catalogService;
        this.tagService=tagService;
    }

    @GetMapping("/menu")
    public Result getArticleTitle(@RequestParam("catalogId") Integer catalogId,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "8") Integer size) {

        return Result.OK().put("page", articleService.getIdAndTitle(page, size, catalogId));
    }


    @GetMapping("/news/{id}")
    public Result getArticleDetail(@PathVariable("id") Long id) {
        return Result.OK().put("news", articleService.getDetail(id));
    }


    @GetMapping("/catalogs")
    public Result getCatalogList(){
        return Result.OK().put("catalogs",catalogService.getCatalogList());
    }

    @GetMapping("/tags")
    public Result getTagList(){
        return Result.OK().put("tags",tagService.getTagList());
    }


    /**
     * 发表文章
     */
    @PostMapping("/news")
    public Result publishArticle(@RequestBody Article article) {


        try {
            ValidatorUtils.validateEntity(article, Add.class);

            articleService.save(article);

            return Result.OK();

        } catch (ParamsException e) {
            return Result.error(HttpStatus.BAD_REQUEST,e.getMsg());
        } catch (TransactionException ee) {
            return Result.error(ee.getMessage());
        }

    }

}
