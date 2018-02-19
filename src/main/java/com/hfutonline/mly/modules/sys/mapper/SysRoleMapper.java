package com.hfutonline.mly.modules.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfutonline.mly.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {


    List<Integer> queryRoleMenuIds(@Param("rid") Integer rid);

}
