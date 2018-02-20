package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.mapper.ArticleMapper;
import com.hfutonline.mly.modules.news.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<Article> page=new PageQuery<Article>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<Article>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
