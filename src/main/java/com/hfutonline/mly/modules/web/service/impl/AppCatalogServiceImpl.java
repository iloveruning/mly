package com.hfutonline.mly.modules.web.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.web.entity.AppCatalog;
import com.hfutonline.mly.modules.web.mapper.AppCatalogMapper;
import com.hfutonline.mly.modules.web.service.AppCatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("appCatalogService")
public class AppCatalogServiceImpl extends ServiceImpl<AppCatalogMapper, AppCatalog> implements AppCatalogService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<AppCatalog> page=new PageQuery<AppCatalog>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<AppCatalog>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addOrUpdate(Integer appId, List<Integer> catalogIdList) {



        if (catalogIdList.size()==0){
            return;
        }

        //先删除旧关系
        if (!this.delete(new EntityWrapper<AppCatalog>().eq("app_id",appId))){
            throw new TransactionException("删除应用与栏目的关系失败");
        }

        AppCatalog appCatalog;
        if (catalogIdList.size()==1){
            appCatalog=new AppCatalog();
            appCatalog.setAppId(appId);
            appCatalog.setCatalogId(catalogIdList.get(0));
            if (!this.insert(appCatalog)){
                throw new TransactionException("增加应用与栏目的关系失败");
            }
            return;
        }

        List<AppCatalog> list=new ArrayList<>();
        for (Integer catalogId:catalogIdList){
            appCatalog=new AppCatalog();
            appCatalog.setAppId(appId);
            appCatalog.setCatalogId(catalogId);
            list.add(appCatalog);
        }

        if (!this.insertBatch(list)){
            throw new TransactionException("增加应用与栏目的关系失败");
        }



    }



}
