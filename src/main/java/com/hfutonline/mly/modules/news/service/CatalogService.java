package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.Catalog;

import java.util.List;
import java.util.Map;

/**
 * 栏目
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface CatalogService extends IService<Catalog> {

    PageInfo queryPage(Map<String, Object> params);

    void save(Catalog catalog);

    List<Integer> getCatalogTagIds(Integer catalogId);

    void update(Catalog catalog);

    List<Catalog> getCatalogList();
}

