package com.hfutonline.mly.modules.web.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.web.entity.Server;
import com.hfutonline.mly.modules.web.mapper.ServerMapper;
import com.hfutonline.mly.modules.web.service.ServerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/2/25
 */
@Service("serverService")
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements ServerService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<Server> page=new PageQuery<Server>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<Server>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

    @Override
    public List<Server> getBaseInfo() {
        return baseMapper.queryBaseInfo();
    }

}
