package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@Data
@TableName("article")
public class Article implements Serializable {


	private static final long serialVersionUID = 8956305525538320811L;
	/**
	 * 新闻id
	 */
	@TableId(type = IdType.AUTO)
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
	 * 文章权重1-100
	 */
	private Integer weight;
	/**
	 * 文章来源
	 */
	private String copyFrom;
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
	private String username;
	/**
	 * 文章未通过的理由
	 */
	private String reason;
	/**
	 * 文章上显示的时间
	 */
	private Date publishTime;
	/**
	 * 新闻提交到后台的时间
	 */
	private Date createTime;
	/**
	 * 新闻最后被修改的时间
	 */
	private Date updateTime;


}
