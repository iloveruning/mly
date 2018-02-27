package com.hfutonline.mly.modules.web.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.web.entity.AppServer;

import java.util.Map;

/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
public interface AppServerService extends IService<AppServer> {

    PageInfo queryPage(Map<String, Object> params);
}

