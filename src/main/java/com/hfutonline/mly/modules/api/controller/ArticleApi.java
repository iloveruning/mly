package com.hfutonline.mly.modules.api.controller;

import com.hfutonline.mly.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
@RestController
@RequestMapping("/open/api")
public class ArticleApi {

    @GetMapping
    public Result getArticle(@RequestParam("catalogId") Integer catalogId){
        return Result.OK().put("catalogId",catalogId);
    }

}
