package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.PageQuery;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.mapper.SysUserMapper;
import com.hfutonline.mly.modules.sys.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public PageInfo<SysUser> queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Page<SysUser> page=new PageQuery<SysUser>(params).getPageParam();
        page=this.selectPage(page,
                new EntityWrapper<SysUser>().like(StringUtils.isNotBlank(username),"username", username));
        return new PageInfo<>(page);
    }

    @Override
    public List<Integer> getUserRoleIds(Integer userId) {
        return baseMapper.queryUserRoleIds(userId);
    }
}
