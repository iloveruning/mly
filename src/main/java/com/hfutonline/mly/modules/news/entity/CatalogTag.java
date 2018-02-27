package com.hfutonline.mly.modules.news.entity;

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
 * @date 2018-02-25 21:20:50
 */
@Data
@TableName("catalog_tag")
public class CatalogTag implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	    @TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 栏目id
	 */
	private Integer catalogId;
	/**
	 * 标签id
	 */
	private Integer tagId;


}
