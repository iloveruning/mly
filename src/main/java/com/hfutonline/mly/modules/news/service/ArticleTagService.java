package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.ArticleTag;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface ArticleTagService extends IService<ArticleTag> {

    PageInfo queryPage(Map<String, Object> params);

    void saveOrUpdate(Long articleId, List<Integer> tagIdList);
}

