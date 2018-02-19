package com.hfutonline.mly.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.sys.entity.SysLog;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface ISysLogService extends IService<SysLog> {

    PageInfo<SysLog> queryPage(Map<String,Object> params);
}
