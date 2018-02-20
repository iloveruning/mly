package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 
 * 
 * @author chenliangliang
 * @email chenliangliang68@163.com
 * @date 2018-02-20 22:56:06
 */
@TableName("article_tag")
public class ArticleTag implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 新闻ID
	 */
	@TableId
	private Long articleId;
	/**
	 * 标签ID
	 */
	private Integer tagId;

	/**
	 * 设置：新闻ID
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：新闻ID
	 */
	public Long getArticleId() {
		return articleId;
	}
	/**
	 * 设置：标签ID
	 */
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	/**
	 * 获取：标签ID
	 */
	public Integer getTagId() {
		return tagId;
	}
}
