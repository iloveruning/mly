package com.hfutonline.mly.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.news.entity.Tag;

import java.util.Map;

/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-20 22:56:06
 */
public interface TagService extends IService<Tag> {

    PageInfo queryPage(Map<String, Object> params);
}

