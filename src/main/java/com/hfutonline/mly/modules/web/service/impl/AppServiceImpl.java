package com.hfutonline.mly.modules.web.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import org.apache.commons.lang3.StringUtils;

import com.hfutonline.mly.modules.web.mapper.AppMapper;
import com.hfutonline.mly.modules.web.entity.App;
import com.hfutonline.mly.modules.web.service.AppService;


@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<App> page=new PageQuery<App>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<App>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
