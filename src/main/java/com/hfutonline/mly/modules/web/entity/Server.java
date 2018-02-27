package com.hfutonline.mly.modules.web.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务器表
 * 
 * @author chenliangliang
 * @date 2018-02-25 19:42:13
 */
@Data
@TableName("server")
public class Server implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 服务器名称
	 */
	private String name;
	/**
	 * 服务器IP
	 */
	private String ip;
	/**
	 * 服务器密码
	 */
	private String password;
	/**
	 * 数据库类型: 1-mysql,2-sql server,3-oracle
	 */
	private Integer sqlType;
	/**
	 * 数据库用户名
	 */
	private String sqlUsername;
	/**
	 * 数据库密码
	 */
	private String sqlPassword;
	/**
	 * 管理员
	 */
	private String username;
	/**
	 * 添加时间
	 */
	private Date createTime;


}
