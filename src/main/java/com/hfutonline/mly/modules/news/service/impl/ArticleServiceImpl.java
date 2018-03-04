package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.mapper.ArticleMapper;
import com.hfutonline.mly.modules.news.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
//        String title = (String)params.get("title");
//        if (StringUtils.isBlank(title)){
//            title=null;
//        }else{
//            title="%"+title;
//        }


        Page<Article> page=new PageQuery<Article>(params).getPageParam();
//        Map<String,Object> map=new HashMap<>();
//        map.put("page",page);
//        map.put("title",title);
        page.setOrderByField(null);
        page.setRecords(this.baseMapper.queryList(page));
        return new PageInfo<>(page);
    }

    @Override
    public Article getDetail(Long id) {
        return baseMapper.queryDetail(id);
    }


    @Override
    public PageInfo<Map<String, Object>> getIdAndTitle(Integer pageNum, Integer pageSize, Integer catalogId) {
        Page<Map<String, Object>> page=new Page<>(pageNum,pageSize);
        page.setRecords(baseMapper.queryIdAndTitle(page,catalogId));
        return new PageInfo<>(page);
    }

}
