package io.renren.mly.modules.news.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageUtils;
import com.hfutonline.mly.common.utils.Query;

import io.renren.mly.modules.news.dao.TagDao;
import io.renren.mly.modules.news.entity.TagEntity;
import io.renren.mly.modules.news.service.TagService;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public PageInfo queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<Tag> page=new PageQuery<Tag>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<Tag>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

}
