package com.hfutonline.mly.modules.sys.shiro.filter;


import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author chenliangliang
 * @date 2018/1/9
 */
public class SessionCountControllerFilter extends AccessControlFilter {


    private Logger logger = LoggerFactory.getLogger(SessionCountControllerFilter.class);
    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;


    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("sessionCount");
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        logger.debug("进行Session个数检查...");
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        //登录后
        Session session = subject.getSession();

        if (kickout(servletRequest, servletResponse, subject, session)) {
            return false;
        }

        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        String name = shiroUser.getName();
        Serializable sessionId = session.getId();


        //读取缓存   没有就创建
        Deque<Serializable> deque = cache.get(name);
        if (deque==null){
            deque=new LinkedList<>();
        }
        //如果队列里没有此sessionId；放入队列
        if (!deque.contains(sessionId)) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(name, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) {
                //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else {
                //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }

            //踢出后再更新下缓存队列
            cache.put(name, deque);
            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }

        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (kickout(servletRequest, servletResponse, subject, session)) {
            return false;
        }
        return true;
    }

    private boolean kickout(ServletRequest servletRequest, ServletResponse servletResponse, Subject subject, Session session) throws IOException {
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(servletRequest);
            //重定向
            WebUtils.issueRedirect(servletRequest, servletResponse, kickoutUrl);
            return true;
        }
        return false;
    }
}
