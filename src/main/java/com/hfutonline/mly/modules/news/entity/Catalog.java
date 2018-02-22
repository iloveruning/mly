package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 栏目
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@Data
@TableName("catalog")
public class Catalog implements Serializable {


	private static final long serialVersionUID = -4937565842301716956L;
	/**
	 * 栏目id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 父级id
	 */
	private Integer pid;
	/**
	 * 栏目名
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 栏目图标
	 */
	private String icon;
	/**
	 * 创建者id
	 */
	private String username;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 父栏目名称
	 */
	@TableField(exist = false)
	private String parentName;


}
