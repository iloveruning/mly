package com.hfutonline.mly.modules.sys.shiro;


import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.shiro.tool.IShiro;
import com.hfutonline.mly.modules.sys.shiro.tool.RealmKit;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author chenliangliang
 * @date 2018/1/8
 */
public class ShiroRealm extends AuthorizingRealm {


    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        logger.debug("进行权限认证...");
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        Integer userId = shiroUser.getId();
        IShiro shiro = RealmKit.me();
        //Set<String> roles = shiro.getRolesByUserId(userId);
        Set<String> permissions = shiro.getPermissionsByUserId(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.debug("进行登录认证...");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        IShiro shiro = RealmKit.me();
        SysUser user = shiro.getUser(token.getUsername());
        ShiroUser shiroUser = shiro.getShiroUser(user);
        System.out.println(user);
        // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        return shiro.info(shiroUser, user, super.getName());

    }

}
