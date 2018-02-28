package com.hfutonline.mly.modules.web.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import com.hfutonline.mly.modules.web.entity.App;

import java.util.List;
import java.util.Map;

/**
 * 应用表
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
public interface AppService extends IService<App> {

    PageInfo queryPage(ShiroUser userId, Map<String, Object> params);

    void register(App app);

    List<Integer> getAppServerIds(Integer id);

    void update(App app);

    List<Integer> getAppCatalogIdList(Integer appId);
}

