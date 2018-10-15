package com.hfutonline.mly.modules.news.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hfutonline.mly.modules.news.entity.Article;
import com.hfutonline.mly.modules.news.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文章管理
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface ArticleMapper extends BaseMapper<Article> {


    /**
     * 列表页查询的信息
     * @param page 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param title
     */
    List<Article> queryList(Pagination page);


    ArticleVo queryDetail(@Param("id") Long id);

    List<Map<String,Object>> queryIdAndTitle(Pagination page,@Param("catalogId") Integer catalogId);

    void increaseReadNum(@Param("id") Long id);
}
