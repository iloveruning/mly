package com.hfutonline.mly.modules.web.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.web.entity.Server;

import java.util.Map;

/**
 * 服务器表
 *
 * @author chenliangliang
 * @date 2018-02-25 19:42:13
 */
public interface ServerService extends IService<Server> {

    PageInfo queryPage(Map<String, Object> params);
}

