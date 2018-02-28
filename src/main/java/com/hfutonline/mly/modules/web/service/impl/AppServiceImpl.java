package com.hfutonline.mly.modules.web.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.Constant;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.common.validator.JwtUtil;
import com.hfutonline.mly.common.validator.Token;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import com.hfutonline.mly.modules.web.entity.App;
import com.hfutonline.mly.modules.web.mapper.AppMapper;
import com.hfutonline.mly.modules.web.service.AppServerService;
import com.hfutonline.mly.modules.web.service.AppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 */
@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    private AppServerService appServerService;

    @Autowired
    protected AppServiceImpl(AppServerService appServerService){
        this.appServerService=appServerService;
    }

    @Override
    public PageInfo queryPage(ShiroUser shiroUser, Map<String, Object> params) {
        String appName = (String)params.get("appName");
        //如果是超级管理员，则加载所有app
        if (shiroUser.getId()== Constant.SUPER_ADMIN){

            Page<App> page=new PageQuery<App>(params).getPageParam();
            page=this.selectPage(page,
                    new EntityWrapper<App>().like(StringUtils.isNotBlank(appName),"app_name", appName));
            return new PageInfo<>(page);
        }else {
            //普通管理员只加载自己注册的app
            Page<App> page=new PageQuery<App>(params).getPageParam();
            page=this.selectPage(page,
                    new EntityWrapper<App>()
                            .eq("username",shiroUser.getName())
                            .like(StringUtils.isNotBlank(appName),"app_name", appName));
            return new PageInfo<>(page);
        }

    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public void register(App app) {

        if (!insert(app)){
           throw new TransactionException("注册应用失败");
        }

        Map<String, Object> map=new HashMap<>(4);
        map.put("id",app.getId());
        map.put("user",app.getUsername());
        Token token = JwtUtil.generateToken(map);

        app.setAppKey(token.getPayload());
        app.setAppSecret(token.getSignature());
        app.setUsername(null);
        app.setDescription(null);
        app.setDeploy(null);

        if (!updateById(app)){
            throw new TransactionException("保存AppKey和AppSecret失败");
        }

        //保存用户与角色关系
        appServerService.saveOrUpdate(app.getId(),app.getServerIdList());


    }

    @Override
    public List<Integer> getAppServerIds(Integer appId) {
        return baseMapper.queryAppServerIds(appId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(App app) {

        if (!this.updateById(app)){
            throw new TransactionException("更新应用信息失败");
        }

        //保存app与server关系
        appServerService.saveOrUpdate(app.getId(),app.getServerIdList());
    }

    @Override
    public List<Integer> getAppCatalogIdList(Integer appId) {
        return baseMapper.queryAppCatalogIds(appId);
    }

}
