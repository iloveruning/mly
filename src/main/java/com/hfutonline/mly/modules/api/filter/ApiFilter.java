package com.hfutonline.mly.modules.api.filter;

import com.alibaba.fastjson.JSON;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.api.auth.ApiRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import springfox.documentation.service.ApiInfo;

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
@WebFilter(urlPatterns = "/open/api/*",filterName = "apiFilter")
public class ApiFilter extends GenericFilterBean {

    private ApiRealm apiRealm;

    @Autowired
    protected ApiFilter(ApiRealm apiRealm){
        this.apiRealm=apiRealm;
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String catalogId = request.getParameter("catalogId");
        if (StringUtils.isBlank(catalogId)) {
            response(response, HttpStatus.NOT_FOUND, "catalogId not found");
            return;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            //Unauthorized
            response(response, HttpStatus.UNAUTHORIZED, "token not found");
            return;
        }

        Integer appId = apiRealm.doAuthentication(token);

        if (appId == null) {
            log.warn(request.getRequestURI()+" |token 认证失败!");
            response(response, HttpStatus.UNAUTHORIZED, "token 认证失败!");
            return;
        }

        ApiInfo apiInfo = apiRealm.doAuthorization(appId);
        Integer cid = Integer.valueOf(catalogId);

        if (!apiInfo.canAccess(cid)) {
            response(response, HttpStatus.UNAUTHORIZED, "权限不足");
            return;
        }

        filterChain.doFilter(request, response);
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



}
