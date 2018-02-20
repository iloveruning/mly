package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.modules.news.entity.ArticleTag;
import com.hfutonline.mly.modules.news.mapper.ArticleTagMapper;
import com.hfutonline.mly.modules.news.service.ArticleTagService;
import org.springframework.stereotype.Service;


@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {


}
