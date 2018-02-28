package com.hfutonline.mly.modules.web.mapper;

import com.hfutonline.mly.modules.web.entity.App;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 应用表
 * 
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
public interface AppMapper extends BaseMapper<App> {

    List<Integer> queryAppServerIds(@Param("appId") Integer appId);

    List<Integer> queryAppCatalogIds(@Param("appId") Integer appId);
}
