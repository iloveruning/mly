package com.hfutonline.mly.modules.api.filter;

import com.alibaba.fastjson.JSON;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.api.auth.ApiInfo;
import com.hfutonline.mly.modules.api.auth.ApiRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenliangliang
 * @date 2018/2/27
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/open/api/*", filterName = "apiFilter")
public class ApiFilter extends GenericFilterBean {

    private ApiRealm apiRealm;

    private static volatile boolean authToken = true;
    private static volatile boolean cross = true;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //token验证
        if (authToken) {
            authToken(request, response);
        }
        //跨域
        if (cross) {
            cross(response);
        }
        filterChain.doFilter(request, response);
    }

    private void cross(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "7200");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,token,Accept,Connection");

    }

    private void authToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            //Unauthorized
            response(response, HttpStatus.UNAUTHORIZED, "token not found");
            return;
        }
        Integer appId = apiRealm.doAuthentication(token);
        if (appId == null) {
            log.warn(request.getRequestURI() + " |token 认证失败!");
            response(response, HttpStatus.UNAUTHORIZED, "token 认证失败!");
            return;
        }

        String requestPath = request.getServletPath();
        if (StringUtils.equals(requestPath, "/open/api/menu")) {
            String catalogId = request.getParameter("catalogId");
            if (StringUtils.isBlank(catalogId)) {
                response(response, HttpStatus.NOT_FOUND, "catalogId not found");
                return;
            }

            ApiInfo apiInfo = apiRealm.doAuthorization(appId);
            Integer cid = Integer.valueOf(catalogId);

            if (!apiInfo.canAccess(cid)) {
                response(response, HttpStatus.UNAUTHORIZED, "权限不足");
            }
        }
    }

    private void response(HttpServletResponse response, HttpStatus status, String msg) {

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSON.toJSONString(Result.error(status, msg)));
        } catch (IOException e) {
            log.error("response to client error : {}", e.getMessage(), e);
        }

    }


    public static void setAuthToken(boolean authToken) {
        ApiFilter.authToken = authToken;
    }

    public static void setCross(boolean cross) {
        ApiFilter.cross = cross;
    }

    public static boolean isAuthToken() {
        return ApiFilter.authToken;
    }

    public static boolean isCross() {
        return ApiFilter.cross;
    }

    @Autowired
    protected ApiFilter(ApiRealm apiRealm) {
        this.apiRealm = apiRealm;
    }


}
