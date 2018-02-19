package com.hfutonline.mly.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.modules.sys.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface ISysMenuService extends IService<SysMenu> {


    /**
     * 获取用户的菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getUserMenus(Integer userId);

}
