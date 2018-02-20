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
@TableName("tag")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 新闻标签的id号
	 */
	@TableId
	private Integer id;
	/**
	 * 新闻标签的名字
	 */
	private String name;
	/**
	 * 父级标签的id号
	 */
	private Integer pid;
	/**
	 * 创建标签的管理员ID号
	 */
	private Integer userId;
	/**
	 * 标签创建的时间
	 */
	private Date createTime;

	/**
	 * 设置：新闻标签的id号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：新闻标签的id号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：新闻标签的名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：新闻标签的名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：父级标签的id号
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父级标签的id号
	 */
	public Integer getPid() {
		return pid;
	}
	/**
	 * 设置：创建标签的管理员ID号
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：创建标签的管理员ID号
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：标签创建的时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：标签创建的时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
