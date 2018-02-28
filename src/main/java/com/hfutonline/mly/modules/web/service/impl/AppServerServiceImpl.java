package com.hfutonline.mly.modules.web.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.modules.web.entity.AppServer;
import com.hfutonline.mly.modules.web.mapper.AppServerMapper;
import com.hfutonline.mly.modules.web.service.AppServerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenliangliang
 */
@Service("appServerService")
public class AppServerServiceImpl extends ServiceImpl<AppServerMapper, AppServer> implements AppServerService {


    @Override
    public void saveOrUpdate(Integer appId, List<Integer> serverIdList) {
        //先删除app与server关系
        if (!this.delete(new EntityWrapper<AppServer>().eq("app_id", appId))) {
            throw new TransactionException("删除app与server关系失败");
        }


        if (serverIdList.size() == 0) {
            return;
        }

        AppServer appServer;

        if (serverIdList.size() == 1) {
            appServer = new AppServer();
            appServer.setAppId(appId);
            appServer.setServerId(serverIdList.get(0));
            if (!this.insert(appServer)) {
                throw new TransactionException("保存app与server关系失败");
            }
            return;
        }

        //保存app与server关系
        List<AppServer> list = new ArrayList<>(serverIdList.size() * 4 / 3 + 1);
        for (Integer serverId : serverIdList) {
            appServer = new AppServer();
            appServer.setAppId(appId);
            appServer.setServerId(serverId);
            list.add(appServer);
        }
        if (!this.insertBatch(list)) {
            throw new TransactionException("保存app与server关系失败");
        }
    }

}
