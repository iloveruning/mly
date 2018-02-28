package com.hfutonline.mly.modules.news.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.news.entity.CatalogTag;
import com.hfutonline.mly.modules.news.mapper.CatalogTagMapper;
import com.hfutonline.mly.modules.news.service.CatalogTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 */
@Service("catalogTagService")
public class CatalogTagServiceImpl extends ServiceImpl<CatalogTagMapper, CatalogTag> implements CatalogTagService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Page<CatalogTag> page = new PageQuery<CatalogTag>(params).getPageParam();
        page = this.selectPage(page,
                new EntityWrapper<CatalogTag>().like(StringUtils.isNotBlank(username), "username", username));
        return new PageInfo<>(page);
    }

    @Override
    public void saveOrUpdate(Integer catalogId, List<Integer> tagIdList) {
        if (tagIdList == null || tagIdList.size() == 0) {
            return;
        }

        //删除栏目和标签的关系
        boolean res = this.delete(new EntityWrapper<CatalogTag>().eq("catalog_id", catalogId));
        if (!res) {
            throw new TransactionException("删除旧的栏目和标签的关系失败");
        }

        CatalogTag catalogTag;
        if (tagIdList.size() == 1) {
            catalogTag = new CatalogTag();
            catalogTag.setCatalogId(catalogId);
            catalogTag.setTagId(tagIdList.get(0));
            if (!this.insert(catalogTag)) {
                throw new TransactionException("保存新的栏目和标签的关系失败");
            }
            return;
        }

        List<CatalogTag> list = new ArrayList<>(tagIdList.size() * 4 / 3 + 1);
        for (Integer tagId : tagIdList) {
            catalogTag = new CatalogTag();
            catalogTag.setCatalogId(catalogId);
            catalogTag.setTagId(tagId);
            list.add(catalogTag);
        }

        if (!this.insertBatch(list)) {
            throw new TransactionException("保存新的栏目和标签的关系失败");
        }
    }

}
