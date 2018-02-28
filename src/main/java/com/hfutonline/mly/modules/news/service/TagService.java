package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.Tag;
import com.hfutonline.mly.modules.web.entity.Server;

import java.util.List;
import java.util.Map;

/**
 * 标签管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:41:59
 */
public interface TagService extends IService<Tag> {

    PageInfo queryPage(Map<String, Object> params);

    List<Server> getBaseInfo();
}

