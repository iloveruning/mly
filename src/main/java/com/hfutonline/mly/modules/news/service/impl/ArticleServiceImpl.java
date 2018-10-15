package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hankcs.hanlp.HanLP;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.mapper.ArticleMapper;
import com.hfutonline.mly.modules.news.service.ArticleService;
import com.hfutonline.mly.modules.news.service.ArticleTagService;
import com.hfutonline.mly.modules.news.vo.ArticleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    private ArticleTagService articleTagService;
    private ThreadPoolTaskExecutor executor;

    @Autowired
    protected ArticleServiceImpl(ArticleTagService articleTagService, ThreadPoolTaskExecutor executor) {
        this.articleTagService = articleTagService;
        this.executor = executor;
    }

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
//        String title = (String)params.get("title");
//        if (StringUtils.isBlank(title)){
//            title=null;
//        }else{
//            title="%"+title;
//        }


        Page<Article> page = new PageQuery<Article>(params).getPageParam();
//        Map<String,Object> map=new HashMap<>();
//        map.put("page",page);
//        map.put("title",title);
        page.setOrderByField(null);
        page.setRecords(this.baseMapper.queryList(page));
        return new PageInfo<>(page);
    }

    @Override
    public ArticleVo getDetail(Long id) {
        executor.execute(() -> baseMapper.increaseReadNum(id));
        return baseMapper.queryDetail(id);
    }


    @Override
    public PageInfo<Map<String, Object>> getIdAndTitle(Integer pageNum, Integer pageSize, Integer catalogId) {
        Page<Map<String, Object>> page = new Page<>(pageNum, pageSize);
        page.setRecords(baseMapper.queryIdAndTitle(page, catalogId));
        return new PageInfo<>(page);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(Article article) {

        if (StringUtils.isBlank(article.getSummary())) {
            List<String> summaryList = HanLP.extractSummary(article.getContent(), 3);
            StringBuilder sb = new StringBuilder();
            summaryList.forEach(s -> sb.append(s).append("，"));
            sb.deleteCharAt(sb.length() - 1);
            sb.append("。");
            article.setSummary(sb.toString());
        }
        if (article.getPublishTime() == null) {
            article.setPublishTime(new Date());
        }
        if (!this.insert(article)) {
            throw new TransactionException("保存文章失败");
        }

        //保存文章与标签关系
        articleTagService.saveOrUpdate(article.getId(), article.getTagIdList());
    }

}
