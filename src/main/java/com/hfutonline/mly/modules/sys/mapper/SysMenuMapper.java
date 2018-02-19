package com.hfutonline.mly.modules.sys.mapper;

import com.hfutonline.mly.modules.sys.entity.SysMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> queryUserMenus(@Param("uid") Integer uid);

}
