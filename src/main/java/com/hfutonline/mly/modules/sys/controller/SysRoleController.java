package com.hfutonline.mly.modules.sys.controller;


import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.sys.entity.SysRole;
import com.hfutonline.mly.modules.sys.service.ISysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private ISysRoleService roleService;

    @Autowired
    protected SysRoleController(ISysRoleService roleService){
        this.roleService=roleService;
    }



    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Result list(@RequestParam Map<String, Object> params){
        System.out.println(params);

        return Result.OK().put("page",roleService.queryPage(params));
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Result select(){
        List<SysRole> list = roleService.selectList(null);

        return Result.OK().put("list", list);
    }


    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Result info(@PathVariable("roleId") Integer roleId){

        SysRole role = roleService.selectById(roleId);

        //查询角色对应的菜单
        List<Integer> menuIdList = roleService.getRoleMenuIds(roleId);
        role.setMenuIdList(menuIdList);

        return Result.OK().put("role", role);
    }

}

