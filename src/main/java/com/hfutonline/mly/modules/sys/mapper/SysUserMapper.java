package com.hfutonline.mly.modules.sys.mapper;

import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> queryAllPerms(@Param("uid") Integer uid);

    List<String> queryAllRoles(@Param("uid") Integer uid);

    List<Integer> queryUserRoleIds(@Param("uid") Integer uid);

}
