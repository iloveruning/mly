package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.ArticleCatalog;
import com.hfutonline.mly.modules.news.mapper.ArticleCatalogMapper;
import com.hfutonline.mly.modules.news.service.ArticleCatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("articleCatalogService")
public class ArticleCatalogServiceImpl extends ServiceImpl<ArticleCatalogMapper, ArticleCatalog> implements ArticleCatalogService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<ArticleCatalog> page=new PageQuery<ArticleCatalog>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<ArticleCatalog>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
