package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.mapper.SysUserMapper;
import com.hfutonline.mly.modules.sys.service.ISysUserRoleService;
import com.hfutonline.mly.modules.sys.service.ISysUserService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private ISysUserRoleService userRoleService;

    @Autowired
    protected SysUserServiceImpl(ISysUserRoleService userRoleService){
        this.userRoleService=userRoleService;
    }

    @Override
    public PageInfo<SysUser> queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<SysUser> page=new PageQuery<SysUser>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<SysUser>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

    @Override
    public List<Integer> getUserRoleIds(Integer userId) {
        return baseMapper.queryUserRoleIds(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(SysUser user) {

        String salt = ShiroKit.getRandomSalt(16);
        user.setSalt(salt);
        String password = ShiroKit.md5(user.getPassword(), salt);
        user.setPassword(password);
       if (!this.insert(user)){
           throw new com.hfutonline.mly.common.exception.TransactionException("保存用户异常");
       }

       //保存用户与角色关系
       userRoleService.saveOrUpdate(user.getId(),user.getRoleIdList());

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(SysUser user) {


        if(org.apache.commons.lang.StringUtils.isBlank(user.getPassword())){
            user.setSalt(null);
            user.setPassword(null);
        }else{
            String salt = ShiroKit.getRandomSalt(16);
            user.setSalt(salt);
            user.setPassword(ShiroKit.md5(user.getPassword(), salt));
        }
        if (!this.updateById(user)){
            throw new TransactionException("更新用户信息失败");
        }
        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getId(),user.getRoleIdList());
    }

    @Override
    public boolean updatePassword(Integer userId, String password, String newPassword) {
        SysUser user=new SysUser();
        user.setPassword(newPassword);
        return this.update(user,new EntityWrapper<SysUser>()
                .eq("id",userId).eq("password",password));
    }
}
