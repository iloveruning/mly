package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.ArticleTag;
import com.hfutonline.mly.modules.news.mapper.ArticleTagMapper;
import com.hfutonline.mly.modules.news.service.ArticleTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Page<ArticleTag> page = new PageQuery<ArticleTag>(params).getPageParam();
        page = this.selectPage(page,
                new EntityWrapper<ArticleTag>().like(StringUtils.isNotBlank(username), "username", username));
        return new PageInfo<>(page);
    }

    @Override
    public void saveOrUpdate(Long articleId, List<Integer> tagIdList) {

        //先删除文章与标签的关系
        if (!this.delete(new EntityWrapper<ArticleTag>().eq("article_id", articleId))) {
            throw new TransactionException("删除文章与标签的关系失败");
        }


        if (tagIdList.size() == 0) {
            return;
        }
        //保存文章与标签关系
        List<ArticleTag> list = new ArrayList<>((int) (tagIdList.size() * 0.75f + 1));
        ArticleTag articleTag;
        for (Integer tagId : tagIdList) {
            articleTag = new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tagId);
            list.add(articleTag);
        }
        if (!this.insertBatch(list)) {
            throw new TransactionException("保存文章与标签关系失败");
        }
    }

}
