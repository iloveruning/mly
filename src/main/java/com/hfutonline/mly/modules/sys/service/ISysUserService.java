package com.hfutonline.mly.modules.sys.service;

import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface ISysUserService extends IService<SysUser> {

    PageInfo<SysUser> queryPage(Map<String,Object> params);

    List<Integer> getUserRoleIds(Integer userId);

}
