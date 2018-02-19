package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.sys.entity.SysLog;
import com.hfutonline.mly.modules.sys.mapper.SysLogMapper;
import com.hfutonline.mly.modules.sys.service.ISysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public PageInfo<SysLog> queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        Page<SysLog> page=new PageQuery<SysLog>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<SysLog>().like(StringUtils.isNotBlank(key),"username", key));
        return new PageInfo<>(page);
    }
}
