package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.vo.ArticleVo;

import java.util.Map;

/**
 * 文章管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface ArticleService extends IService<Article> {

    PageInfo queryPage(Map<String, Object> params);

    ArticleVo getDetail(Long id);


    PageInfo<Map<String,Object>> getIdAndTitle(Integer pageNum, Integer pageSize, Integer catalogId);

    void save(Article article);
}

