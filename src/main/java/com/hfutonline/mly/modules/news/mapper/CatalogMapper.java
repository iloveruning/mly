package com.hfutonline.mly.modules.news.mapper;

import com.hfutonline.mly.modules.news.entity.Catalog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 栏目
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface CatalogMapper extends BaseMapper<Catalog> {

    List<Integer> queryCatalogTagIds(@Param("catalogId") Integer catalogId);

    List<Catalog> queryCatalogIdAndName();

}
