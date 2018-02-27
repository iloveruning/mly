package com.hfutonline.mly.modules.web.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import org.apache.commons.lang3.StringUtils;

import com.hfutonline.mly.modules.web.mapper.AppCatalogMapper;
import com.hfutonline.mly.modules.web.entity.AppCatalog;
import com.hfutonline.mly.modules.web.service.AppCatalogService;


@Service("appCatalogService")
public class AppCatalogServiceImpl extends ServiceImpl<AppCatalogMapper, AppCatalog> implements AppCatalogService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<AppCatalog> page=new PageQuery<AppCatalog>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<AppCatalog>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
