package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.sys.entity.SysRole;
import com.hfutonline.mly.modules.sys.entity.SysRoleMenu;
import com.hfutonline.mly.modules.sys.entity.SysUserRole;
import com.hfutonline.mly.modules.sys.mapper.SysRoleMapper;
import com.hfutonline.mly.modules.sys.service.ISysRoleMenuService;
import com.hfutonline.mly.modules.sys.service.ISysRoleService;
import com.hfutonline.mly.modules.sys.service.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    private ISysRoleMenuService roleMenuService;

    private ISysUserRoleService userRoleService;

    @Autowired
    protected SysRoleServiceImpl(ISysRoleMenuService roleMenuService,ISysUserRoleService userRoleService) {
        this.roleMenuService = roleMenuService;
        this.userRoleService=userRoleService;
    }

    @Override
    public PageInfo<SysRole> queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Page<SysRole> page = new PageQuery<SysRole>(params).getPageParam();
        page = this.selectPage(page,
                new EntityWrapper<SysRole>().like(StringUtils.isNotBlank(roleName), "name", roleName));
        return new PageInfo<>(page);
    }

    @Override
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return baseMapper.queryRoleMenuIds(roleId);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(SysRole role) {

        if (!this.insert(role)) {
            throw new TransactionException("保存角色信息失败");
        }
        //保存角色与菜单关系
        roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(SysRole role) {

        if (!this.updateById(role)) {
            throw new TransactionException("跟新角色信息失败");
        }

        //更新角色与菜单关系
        roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBatch(Integer[] roleIds) {


        if (!this.deleteBatchIds(Arrays.asList(roleIds))) {
            throw new TransactionException("删除角色失败");
        }

        //删除角色与菜单关联
        boolean res1 = roleMenuService.delete(new EntityWrapper<SysRoleMenu>().in("role_id",roleIds));
        if (!res1) {
            throw new TransactionException("删除角色与菜单关联关系失败");
        }

        boolean res2= userRoleService.delete(new EntityWrapper<SysUserRole>().in("role_id",roleIds));
        if (!res2) {
            throw new TransactionException("删除角色与用户关联关系失败");
        }


    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Integer roleId){
        if (!this.deleteById(roleId)) {
            throw new TransactionException("删除角色失败");
        }

        boolean res1 = roleMenuService.delete(new EntityWrapper<SysRoleMenu>().eq("role_id", roleId));
        if (!res1) {
            throw new TransactionException("删除角色与菜单关联关系失败");
        }

        boolean res2= userRoleService.delete(new EntityWrapper<SysUserRole>().eq("role_id",roleId));
        if (!res2) {
            throw new TransactionException("删除角色与用户关联关系失败");
        }
    }
}
