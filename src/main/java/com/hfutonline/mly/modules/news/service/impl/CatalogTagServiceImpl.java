package com.hfutonline.mly.modules.news.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import org.apache.commons.lang3.StringUtils;

import com.hfutonline.mly.modules.news.mapper.CatalogTagMapper;
import com.hfutonline.mly.modules.news.entity.CatalogTag;
import com.hfutonline.mly.modules.news.service.CatalogTagService;


@Service("catalogTagService")
public class CatalogTagServiceImpl extends ServiceImpl<CatalogTagMapper, CatalogTag> implements CatalogTagService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<CatalogTag> page=new PageQuery<CatalogTag>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<CatalogTag>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
