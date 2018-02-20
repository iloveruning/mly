package com.hfutonline.mly.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface ISysRoleService extends IService<SysRole> {

    PageInfo<SysRole> queryPage(Map<String,Object> params);


    List<Integer> getRoleMenuIds(Integer roleId);

    void save(SysRole role);

    void update(SysRole role);

    void deleteBatch(Integer[] roleIds);

    void delete(Integer roleId);
}
