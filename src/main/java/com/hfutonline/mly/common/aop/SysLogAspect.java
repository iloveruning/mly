package com.hfutonline.mly.common.aop;

import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.utils.HttpContextUtil;
import com.hfutonline.mly.common.utils.IPUtil;
import com.hfutonline.mly.modules.sys.service.ISysLogService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 系统日志，切面处理类
 *
 * @author chenliangliang
 * @date 2018/2/19
 */
@Component
@Aspect
public class SysLogAspect {


    private ISysLogService logService;

    @Autowired
    protected SysLogAspect(ISysLogService logService) {
        this.logService = logService;
    }


    @Pointcut("@annotation(com.hfutonline.mly.common.annotation.SysLog)")
    public void logPointCut() {

    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        //执行方法
        Object res = joinPoint.proceed();
        //方法用时
        long time = System.currentTimeMillis() - start;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.hfutonline.mly.modules.sys.entity.SysLog log = new com.hfutonline.mly.modules.sys.entity.SysLog();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            //注解上的描述
            log.setOperation(sysLog.value());
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        log.setMethod(className + ":" + methodName);
        //请求的参数
        Object[] args = joinPoint.getArgs();
        log.setParams(Arrays.toString(args));
        //设置IP地址
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        log.setIp(IPUtil.getIpAddr(request));
        //用户名
        log.setUsername(ShiroKit.getUserName());
        //操作用时
        log.setTime(time);
        //保存系统日志
        logService.insert(log);

        return res;
    }


}
