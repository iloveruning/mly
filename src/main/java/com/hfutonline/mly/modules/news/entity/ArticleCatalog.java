package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@Data
@TableName("article_catalog")
public class ArticleCatalog implements Serializable {


	private static final long serialVersionUID = 621887456993324547L;
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 栏目id
	 */
	private Integer catalogId;


}
