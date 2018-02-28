package com.hfutonline.mly.modules.news.mapper;

import com.hfutonline.mly.modules.news.entity.Tag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfutonline.mly.modules.web.entity.Server;

import java.util.List;

/**
 * 标签管理
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:41:59
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Server> queryBaseInfo();
}
