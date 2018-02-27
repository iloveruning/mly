package com.hfutonline.mly.modules.web.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.web.entity.App;

import java.util.Map;

/**
 * 应用表
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
public interface AppService extends IService<App> {

    PageInfo queryPage(Map<String, Object> params);
}

