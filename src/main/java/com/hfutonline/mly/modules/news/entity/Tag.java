package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签管理
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:41:59
 */
@Data
@TableName("tag")
public class Tag implements Serializable {


	private static final long serialVersionUID = 300980957403661799L;
	/**
	 * 标签的id号
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * 标签的名字
	 */
	@NotBlank(message = "标签名不能为空")
	private String name;
	/**
	 * 标签的描述
	 */
	private String description;
	/**
	 * 创建标签的管理员ID号
	 */
	private String username;
	/**
	 * 标签创建的时间
	 */
	private Date createTime;


}
