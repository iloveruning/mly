package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.CatalogTag;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:20:50
 */
public interface CatalogTagService extends IService<CatalogTag> {

    PageInfo queryPage(Map<String, Object> params);

    void saveOrUpdate(Integer catalogId, List<Integer> tagIdList);
}

