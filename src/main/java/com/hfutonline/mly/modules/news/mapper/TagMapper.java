package com.hfutonline.mly.modules.news.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfutonline.mly.modules.news.entity.Tag;
import com.hfutonline.mly.modules.news.vo.TagVo;

import java.util.List;

/**
 * 标签管理
 * 
 * @author chenliangliang
 * @date 2018-02-21 15:41:59
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<TagVo> selectTagIdAndName();
}
