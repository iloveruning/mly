package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.Catalog;
import com.hfutonline.mly.modules.news.mapper.CatalogMapper;
import com.hfutonline.mly.modules.news.service.CatalogService;
import com.hfutonline.mly.modules.news.service.CatalogTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 */
@Service("catalogService")
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {

    private CatalogTagService catalogTagService;

    @Autowired
    protected CatalogServiceImpl(CatalogTagService catalogTagService) {
        this.catalogTagService = catalogTagService;
    }

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Page<Catalog> page = new PageQuery<Catalog>(params).getPageParam();
        page = this.selectPage(page,
                new EntityWrapper<Catalog>().like(StringUtils.isNotBlank(username), "username", username));
        return new PageInfo<>(page);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(Catalog catalog) {

        if (!this.insert(catalog)) {
            throw new TransactionException("保存栏目信息失败");
        }

        //保存栏目与标签关系
        catalogTagService.saveOrUpdate(catalog.getId(), catalog.getTagIdList());
    }

    @Override
    public List<Integer> getCatalogTagIds(Integer catalogId) {
        return baseMapper.queryCatalogTagIds(catalogId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(Catalog catalog) {

        if (!updateById(catalog)) {
            throw new TransactionException("更新栏目信息失败");
        }

        //更新栏目和标签的关系
        catalogTagService.saveOrUpdate(catalog.getId(), catalog.getTagIdList());
    }

}
