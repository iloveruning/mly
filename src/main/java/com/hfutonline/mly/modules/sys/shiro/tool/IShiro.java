package com.hfutonline.mly.modules.sys.shiro.tool;

import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.Set;

/**
 * @author chenliangliang
 * @date 2018/1/9
 */
public interface IShiro {

    /**
     * 根据用户名获取用户
     * @param name 用户名
     * @return 用户
     */
    SysUser getUser(String name);


    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     * @return shiroy用户
     */
    ShiroUser getShiroUser(SysUser user);


    /**
     * 根据用户id获取角色名称
     *
     * @param userId 用户id
     */
   Set<String> getRolesByUserId(Integer userId);


    /**
     * 获取权限列表通过角色id
     *
     * @param userId 用户id
     */
    Set<String> getPermissionsByUserId(Integer userId);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, SysUser user, String realName);
}
