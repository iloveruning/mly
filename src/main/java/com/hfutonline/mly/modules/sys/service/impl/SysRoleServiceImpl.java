package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.sys.entity.SysRole;
import com.hfutonline.mly.modules.sys.mapper.SysRoleMapper;
import com.hfutonline.mly.modules.sys.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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


    @Override
    public PageInfo<SysRole> queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        Page<SysRole> page=new PageQuery<SysRole>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<SysRole>().like(StringUtils.isNotBlank(roleName),"name", roleName));
        return new PageInfo<>(page);
    }

    @Override
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return baseMapper.queryRoleMenuIds(roleId);
    }
}
