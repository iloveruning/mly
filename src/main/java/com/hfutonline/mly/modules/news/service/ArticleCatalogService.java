package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.ArticleCatalog;

import java.util.Map;

/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface ArticleCatalogService extends IService<ArticleCatalog> {

    PageInfo queryPage(Map<String, Object> params);
}

