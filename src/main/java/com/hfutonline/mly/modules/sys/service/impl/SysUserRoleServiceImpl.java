package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.modules.sys.entity.SysUserRole;
import com.hfutonline.mly.modules.sys.mapper.SysUserRoleMapper;
import com.hfutonline.mly.modules.sys.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void saveOrUpdate(Integer userId, List<Integer> roleIdList) {
        //先删除用户与角色关系
        if (!this.delete(new EntityWrapper<SysUserRole>().eq("user_id",userId))){
            throw new TransactionException("删除用户与角色关系失败");
        }


        if(roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        List<SysUserRole> list = new ArrayList<>(roleIdList.size()*4/3+1);
        for(Integer roleId : roleIdList){
            SysUserRole sysUserRoleEntity = new SysUserRole();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);
            list.add(sysUserRoleEntity);
        }
        if (!this.insertBatch(list)){
            throw new TransactionException("保存用户与角色关系失败");
        }

    }
}
