package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenliangliang
 * @email chenliangliang68@163.com
 * @date 2018-02-20 22:56:06
 */
@TableName("article")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 新闻id
	 */
	@TableId
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 状态：0-未审核，1-审核未通过，2-审核通过，3-优秀
	 */
	private Integer state;
	/**
	 * 新闻封面
	 */
	private String cover;
	/**
	 * 阅读量
	 */
	private Integer read;
	/**
	 * 发布文章的管理员
	 */
	private Integer userId;
	/**
	 * 新闻提交到后台的时间
	 */
	private Date createTime;
	/**
	 * 新闻最后被修改的时间
	 */
	private Date updateTime;

	/**
	 * 设置：新闻id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：新闻id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：摘要
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 获取：摘要
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：状态：0-未审核，1-审核未通过，2-审核通过，3-优秀
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：状态：0-未审核，1-审核未通过，2-审核通过，3-优秀
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 设置：新闻封面
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * 获取：新闻封面
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * 设置：阅读量
	 */
	public void setRead(Integer read) {
		this.read = read;
	}
	/**
	 * 获取：阅读量
	 */
	public Integer getRead() {
		return read;
	}
	/**
	 * 设置：发布文章的管理员
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：发布文章的管理员
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：新闻提交到后台的时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：新闻提交到后台的时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：新闻最后被修改的时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：新闻最后被修改的时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
