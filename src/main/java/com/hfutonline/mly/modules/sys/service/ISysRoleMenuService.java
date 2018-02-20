package com.hfutonline.mly.modules.sys.service;

import com.hfutonline.mly.modules.sys.entity.SysRoleMenu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    void saveOrUpdate(Integer id, List<Integer> menuIdList);
}
