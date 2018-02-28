package com.hfutonline.mly.modules.api.auth;


import lombok.Data;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
@Data
public class ApiInfo {

    private Integer appId;

    private List<Integer> catalogIdList;

    public ApiInfo(Integer appId, List<Integer> catalogIdList) {
        this.appId = appId;
        this.catalogIdList = catalogIdList;
    }

    public boolean canAccess(Integer catalogId) {
        return this.catalogIdList != null && this.catalogIdList.contains(catalogId);
    }
}
