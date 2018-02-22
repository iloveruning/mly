package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.ArticleTag;
import com.hfutonline.mly.modules.news.mapper.ArticleTagMapper;
import com.hfutonline.mly.modules.news.service.ArticleTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<ArticleTag> page=new PageQuery<ArticleTag>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<ArticleTag>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
