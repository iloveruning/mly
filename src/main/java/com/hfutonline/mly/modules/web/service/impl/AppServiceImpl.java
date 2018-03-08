package com.hfutonline.mly.modules.web.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.MlyException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.Constant;
import com.hfutonline.mly.common.utils.EhcacheTemplate;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.common.validator.JwtUtil;
import com.hfutonline.mly.common.validator.Token;
import com.hfutonline.mly.modules.api.auth.ApiRealm;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import com.hfutonline.mly.modules.web.entity.App;
import com.hfutonline.mly.modules.web.entity.AppCatalog;
import com.hfutonline.mly.modules.web.entity.AppServer;
import com.hfutonline.mly.modules.web.mapper.AppMapper;
import com.hfutonline.mly.modules.web.service.AppCatalogService;
import com.hfutonline.mly.modules.web.service.AppServerService;
import com.hfutonline.mly.modules.web.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author chenliangliang
 */
@Slf4j
@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    private AppServerService appServerService;

    private EhcacheTemplate cacheTemplate;

    private AppCatalogService appCatalogService;

    @Autowired
    protected AppServiceImpl(AppServerService appServerService, EhcacheTemplate cacheTemplate,
                             AppCatalogService appCatalogService) {
        this.appServerService = appServerService;
        this.cacheTemplate = cacheTemplate;
        this.appCatalogService = appCatalogService;
    }

    @Override
    public PageInfo queryPage(ShiroUser shiroUser, Map<String, Object> params) {
        String appName = (String) params.get("appName");
        //如果是超级管理员，则加载所有app
        if (shiroUser.getId() == Constant.SUPER_ADMIN) {

            Page<App> page = new PageQuery<App>(params).getPageParam();
            page = this.selectPage(page,
                    new EntityWrapper<App>().like(StringUtils.isNotBlank(appName), "app_name", appName));
            return new PageInfo<>(page);
        } else {
            //普通管理员只加载自己注册的app
            Page<App> page = new PageQuery<App>(params).getPageParam();
            page = this.selectPage(page,
                    new EntityWrapper<App>()
                            .eq("username", shiroUser.getName())
                            .like(StringUtils.isNotBlank(appName), "app_name", appName));
            return new PageInfo<>(page);
        }

    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public void register(App app) {

        if (!insert(app)) {
            throw new TransactionException("注册应用失败");
        }

        Map<String, Object> map = new HashMap<>(4);
        map.put("id", app.getId());
        map.put("user", app.getUsername());
        Token token = JwtUtil.generateToken(map);

        app.setAppKey(token.getPayload());
        app.setAppSecret(token.getSignature());
        app.setUsername(null);
        app.setDescription(null);
        app.setDeploy(null);

        if (!updateById(app)) {
            throw new TransactionException("保存AppKey和AppSecret失败");
        }

        //保存用户与角色关系
        appServerService.saveOrUpdate(app.getId(), app.getServerIdList());


    }

    @Override
    public List<Integer> getAppServerIds(Integer appId) {
        return baseMapper.queryAppServerIds(appId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(App app) {

        if (!this.updateById(app)) {
            throw new TransactionException("更新应用信息失败");
        }

        //保存app与server关系
        appServerService.saveOrUpdate(app.getId(), app.getServerIdList());
    }

    @Override
    public List<Integer> getAppCatalogIdList(Integer appId) {
        return baseMapper.queryAppCatalogIds(appId);
    }

    @Override
    public void delete(Integer[] ids) {

        int error=0;
        for (Integer id : ids) {
            try {
                delete(id);
                cacheTemplate.cacheEvict(ApiRealm.cacheName, ApiRealm.prefix + id);
            } catch (TransactionException e) {
                error++;
               log.warn("delete {} app fail",id,e);
            }
        }

        if (error>0){
            throw new MlyException("删除失败 "+error+" 个,请重新删除");
        }
    }

    /**
     * Propagation.NESTED
     * 如果当前上下文中存在事务，则以嵌套事务执行该方法，也就说，这部分方法是外部方法的一部分，调用者回滚，则该方法回滚，但如果该方法自己发生异常，则自己回滚，不会影响外部事务，如果不存在事务，则与PROPAGATION_REQUIRED一样
     */
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.NESTED)
    public void delete(Integer appId) {

        if (!this.deleteById(appId)) {
            throw new TransactionException("删除Id为 " + appId + " 的应用失败");
        }

        if (!appCatalogService.delete(new EntityWrapper<AppCatalog>().eq("app_id", appId))) {
            throw new TransactionException("删除Id为 " + appId + " 的应用与栏目的关系失败");
        }

        if (!appServerService.delete(new EntityWrapper<AppServer>().eq("app_id", appId))) {
            throw new TransactionException("删除Id为 " + appId + " 的应用与服务器的关系失败");
        }

    }

}
