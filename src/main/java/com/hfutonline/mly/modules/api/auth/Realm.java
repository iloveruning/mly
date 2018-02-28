package com.hfutonline.mly.modules.api.auth;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
public interface Realm {

    /**
     * token认证
     *
     * @param jwtToken jwtToken
     * @return AppId
     */
    Integer doAuthentication(String jwtToken);


    /**
     * api权限认证
     * @param appId AppId
     * @return apiInfo
     */
    ApiInfo doAuthorization(Integer appId);

}
