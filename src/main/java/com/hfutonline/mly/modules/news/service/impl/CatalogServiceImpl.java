package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.Catalog;
import com.hfutonline.mly.modules.news.mapper.CatalogMapper;
import com.hfutonline.mly.modules.news.service.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("catalogService")
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<Catalog> page=new PageQuery<Catalog>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<Catalog>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
