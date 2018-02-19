package com.hfutonline.mly.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Data
@Accessors(chain = true)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer pid;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;

    /**
     * 子菜单
     */
    @TableField(exist=false)
    private List<SysMenu> list;

    /**
     * 父菜单名称
     */
    @TableField(exist=false)
    private String parentName;


}
