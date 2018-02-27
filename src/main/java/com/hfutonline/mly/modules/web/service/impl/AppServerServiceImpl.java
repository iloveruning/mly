package com.hfutonline.mly.modules.web.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import org.apache.commons.lang3.StringUtils;

import com.hfutonline.mly.modules.web.mapper.AppServerMapper;
import com.hfutonline.mly.modules.web.entity.AppServer;
import com.hfutonline.mly.modules.web.service.AppServerService;


@Service("appServerService")
public class AppServerServiceImpl extends ServiceImpl<AppServerMapper, AppServer> implements AppServerService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<AppServer> page=new PageQuery<AppServer>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<AppServer>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
