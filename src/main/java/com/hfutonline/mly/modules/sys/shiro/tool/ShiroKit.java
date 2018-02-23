package com.hfutonline.mly.modules.sys.shiro.tool;

import com.hfutonline.mly.common.utils.RandomUtil;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Shiro工具类
 *
 * @author chenliangliang
 * @date 2018/1/9
 */
public class ShiroKit {

    /**
     * 加密算法
     */
    public static final String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 循环次数
     */
    public static final int HASH_ITERATIONS = 32;


    /**
     * shiro密码加密工具类
     *
     * @param password 密码
     * @param salt     密码盐
     * @return 返回加密后的密码
     */
    public static String md5(String password, String salt) {
        ByteSource s = new Md5Hash(salt);
        return new SimpleHash(HASH_ALGORITHM_NAME, password, s, HASH_ITERATIONS).toString();
    }

    /**
     * 获取随机盐值
     *
     * @param length
     * @return
     */
    public static String getRandomSalt(int length) {
        return RandomUtil.randomString(length);
    }

    /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    /**
     * 从shiro中获取session
     *
     * @return session
     */
    public static Session getSession() {
        return getSubject().getSession();
    }


    /**
     * 通过key获取shiro指定的session属性
     *
     * @param key 键
     * @return 值
     */
    public static Object getSessionAttr(String key) {
        Session session = getSession();
        return session != null ? session.getAttribute(key) : null;
    }

    /**
     * 设置shiro指定的session属性
     *
     * @param key
     * @param value
     */
    public static void setSessionAttr(String key, Object value) {
        Session session = getSession();
        session.setAttribute(key, value);
    }

    /**
     * 移除shiro指定的session属性
     */
    public static Object removeSessionAttr(String key) {
        Session session = getSession();
        return session != null ? session.removeAttribute(key) : null;
    }


    /**
     * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
     *
     * @param roleName 角色名
     * @return 属于该角色：true，否则false
     */
    public static boolean hasRole(String roleName) {
        Subject subject = getSubject();
        return subject != null && roleName != null && subject.hasRole(roleName);
    }


    /**
     * 验证当前用户是否拥有所有角色
     *
     * @param roles 角色集
     * @return
     */
    public static boolean hasAllRoles(String... roles) {
        if (roles.length == 0) {
            return false;
        }
        List<String> list = Arrays.asList(roles);
        Subject subject = getSubject();
        return subject != null && subject.hasAllRoles(list);
    }

    /**
     * 验证当前用户是否拥有所有角色
     *
     * @param roles 角色集
     * @return
     */
    public static boolean hasAllRoles(Collection<String> roles) {
        if (roles.size() == 0) {
            return false;
        }
        Subject subject = getSubject();
        return subject != null && subject.hasAllRoles(roles);
    }


    /**
     * 验证当前用户是否拥有指定权限
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        Subject subject = getSubject();
        return subject != null && permission != null && subject.isPermitted(permission);
    }

    /**
     * 验证当前用户是否拥有指定权限集
     *
     * @param permissions 权限集
     * @return 拥有权限：true，否则false
     */
    public static boolean hasAllPermissions(String... permissions) {
        if (permissions.length == 0) {
            return false;
        }
        Subject subject = getSubject();
        return subject != null && subject.isPermittedAll(permissions);
    }


    /**
     * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。
     *
     * @return 通过身份验证：true，否则false
     */
    public static boolean isAuthenticated() {
        Subject subject = getSubject();
        return subject != null && subject.isAuthenticated();
    }


    /**
     * 认证通过或已记住的用户。与guset搭配使用。
     *
     * @return 用户：true，否则 false
     */
    public static boolean isUser() {
        Subject subject = getSubject();
        return subject != null && subject.getPrincipal() != null;
    }


    /**
     * 是否记住密码
     */
    public static boolean isRemembered() {
        Subject subject = getSubject();
        return subject != null && subject.isRemembered();
    }

    /**
     * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
     *
     * @return 访客：true，否则false
     */
    public static boolean isGuest() {
        return !isUser();
    }


    /**
     * 退出登录
     */
    public static void logout() {
        Subject subject = getSubject();
        if (subject != null) {
            subject.logout();
        }
    }

    /**
     * 获取shiro用户
     *
     * @return shiro用户
     */
    public static ShiroUser getPrincipal() {
        Subject subject = getSubject();
        if (subject != null) {
            return (ShiroUser) subject.getPrincipal();
        }
        return null;
    }


    /**
     * 获取登录用户名
     */
    public static String getUserName() {
        ShiroUser shiroUser = getPrincipal();
        return shiroUser == null ? null : shiroUser.getName();
    }


    /**
     * 获取登录用户名
     */
    public static Integer getUserId() {
        ShiroUser shiroUser = getPrincipal();
        return shiroUser == null ? null : shiroUser.getId();
    }

}
