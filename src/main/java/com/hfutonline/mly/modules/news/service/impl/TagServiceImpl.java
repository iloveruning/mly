package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.Tag;
import com.hfutonline.mly.modules.news.mapper.TagMapper;
import com.hfutonline.mly.modules.news.service.TagService;
import com.hfutonline.mly.modules.web.entity.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<Tag> page=new PageQuery<Tag>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<Tag>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

    @Override
    public List<Server> getBaseInfo() {
        return baseMapper.queryBaseInfo();
    }

}
