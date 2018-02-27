package com.hfutonline.mly.modules.web.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 应用表
 * 
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@Data
@TableName("app")
public class App implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	    @TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 应用的名称
	 */
	private String appName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 部署文件夹
	 */
	private String deploy;
	/**
	 * 应用的key
	 */
	private String appKey;
	/**
	 * 应用的secret
	 */
	private String appSecret;
	/**
	 * 创建应用的管理员
	 */
	private String username;
	/**
	 * 应用创建的时间
	 */
	private Date createTime;


}
