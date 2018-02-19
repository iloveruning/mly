package com.hfutonline.mly.modules.sys.shiro.tool;

import com.hfutonline.mly.common.utils.Constant;
import com.hfutonline.mly.common.utils.SpringContextHolder;
import com.hfutonline.mly.modules.sys.entity.SysMenu;
import com.hfutonline.mly.modules.sys.mapper.SysMenuMapper;
import com.hfutonline.mly.modules.sys.mapper.SysUserMapper;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenliangliang
 * @date 2018/1/9
 */
@Service
@DependsOn("springContextHolder")
public class RealmKit implements IShiro {


    private SysUserMapper userMapper;

    private SysMenuMapper menuMapper;

    @Autowired
    protected RealmKit(SysUserMapper userMapper,SysMenuMapper menuMapper){
        this.userMapper=userMapper;
        this.menuMapper=menuMapper;
    }


    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public SysUser getUser(String name) {

        SysUser user = new SysUser();
        user.setUsername(name);
        user = userMapper.selectOne(user);
        // 账号不存在
        if (null == user) {
            throw new UnknownAccountException();
        }
        // 账号被冻结
        if (user.getStatus() == 0) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser getShiroUser(SysUser user) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());
        shiroUser.setName(user.getUsername());
        return shiroUser;
    }

    @Override
    public Set<String> getRolesByUserId(Integer userId) {
        return new HashSet<>(userMapper.queryAllRoles(userId));
    }

    @Override
    public Set<String> getPermissionsByUserId(Integer userId) {

        Set<String> permsSet = new HashSet<>();
        List<String> perms;
        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenu> menuList = menuMapper.selectList(null);
            perms = new ArrayList<>(menuList.size()*4/3);
            for (SysMenu menu : menuList) {
                perms.add(menu.getPerms());
            }

        } else {
            perms = userMapper.queryAllPerms(userId);
        }

        for (String perm : perms) {
            if (StringUtils.isBlank(perm)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perm.trim().split(",")));
        }
        return permsSet;
    }


    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, SysUser user, String realName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realName);
    }
}
