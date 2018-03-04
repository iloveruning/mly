package com.hfutonline.mly.modules.api.controller;

import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.news.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
@RestController
@RequestMapping("/open/api")
public class ArticleApi {

    private ArticleService articleService;

    @Autowired
    protected ArticleApi(ArticleService articleService){
        this.articleService=articleService;
    }

    @GetMapping("/menu")
    public Result getArticleTitle(@RequestParam("catalogId") Integer catalogId,
                             @RequestParam("page") Integer page,
                             @RequestParam("size") Integer size){
        return Result.OK().put("page",articleService.getIdAndTitle(page,size,catalogId));
    }


    @GetMapping("/news/{id}")
    public Result getArticleDetail(@PathVariable("id") Long id){

        return Result.OK().put("news",articleService.getDetail(id));
    }

}
