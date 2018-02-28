package com.hfutonline.mly.modules.web.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.modules.web.entity.AppServer;

import java.util.List;

/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
public interface AppServerService extends IService<AppServer> {



    void saveOrUpdate(Integer id, List<Integer> serverIdList);
}

