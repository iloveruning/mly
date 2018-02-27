package com.hfutonline.mly.modules.web.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@Data
@TableName("app_catalog")
public class AppCatalog implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	    @TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 应用id
	 */
	private Integer appId;
	/**
	 * 栏目id
	 */
	private Integer catalog;


}
