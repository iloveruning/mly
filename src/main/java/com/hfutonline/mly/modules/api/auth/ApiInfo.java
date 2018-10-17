package com.hfutonline.mly.modules.api.auth;


import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenliangliang
 * @date 2018/2/28
 */
@Data
public class ApiInfo {

    private Integer appId;

    private Set<Integer> catalogIdSet=new HashSet<>();

    public ApiInfo(Integer appId, List<Integer> catalogIdList) {
        this.appId = appId;
        this.catalogIdSet.addAll(catalogIdList);
    }

    public boolean canAccess(Integer catalogId) {
        return this.catalogIdSet.contains(catalogId);
    }
}
