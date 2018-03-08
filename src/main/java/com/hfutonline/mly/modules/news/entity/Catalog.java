package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.hfutonline.mly.common.validator.group.Add;
import com.hfutonline.mly.common.validator.group.Update;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 栏目名
     */
    @NotBlank(message = "栏目名不能为空", groups = {Add.class, Update.class})
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
     * 类型：0-版块，1-栏目
     */
    private Integer type;
    /**
     * 创建者id
     */
    private String username;
    /**
     * 创建时间
     */
    private Date createTime;


    @TableField(exist = false)
    private List<Integer> tagIdList;


    @TableField(exist = false)
    private boolean checked = false;
}
