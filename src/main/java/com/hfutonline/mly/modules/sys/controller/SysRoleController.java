package com.hfutonline.mly.modules.sys.controller;


import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.validator.ValidatorUtils;
import com.hfutonline.mly.modules.sys.entity.SysRole;
import com.hfutonline.mly.modules.sys.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@Slf4j
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private ISysRoleService roleService;

    @Autowired
    protected SysRoleController(ISysRoleService roleService) {
        this.roleService = roleService;
    }


    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Result list(@RequestParam Map<String, Object> params) {
        System.out.println(params);

        return Result.OK().put("page", roleService.queryPage(params));
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Result select() {
        List<SysRole> list = roleService.selectList(null);

        return Result.OK().put("list", list);
    }


    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Result info(@PathVariable("roleId") Integer roleId) {

        SysRole role = roleService.selectById(roleId);

        //查询角色对应的菜单
        List<Integer> menuIdList = roleService.getRoleMenuIds(roleId);
        role.setMenuIdList(menuIdList);

        return Result.OK().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Result save(@RequestBody SysRole role) {
        System.out.println(role);

        try {
            ValidatorUtils.validateEntity(role);
            roleService.save(role);
            return Result.OK();
        } catch (ParamsException e) {
            log.error("参数验证错误:{}", e.getMsg(), e);
            return Result.error(HttpStatus.BAD_REQUEST, e.getMsg());
        } catch (TransactionException ee) {
            log.error("保存失败:{}", ee.getMessage(), ee);
            return Result.error(ee.getMessage());
        }

    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Result update(@RequestBody SysRole role) {

        try {
            ValidatorUtils.validateEntity(role);
            roleService.update(role);
            return Result.OK();
        } catch (ParamsException e) {
            log.error("参数验证错误:{}", e.getMsg(), e);
            return Result.error(HttpStatus.BAD_REQUEST, e.getMsg());
        } catch (TransactionException ee) {
            log.error("更新失败:{}", ee.getMessage(), ee);
            return Result.error(ee.getMessage());
        }

    }


    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public Result delete(@RequestBody Integer[] roleIds) {

        if (roleIds.length == 0) {
            return Result.OK();
        }
        try {
            if (roleIds.length == 1) {
                roleService.delete(roleIds[0]);
            } else {
                roleService.deleteBatch(roleIds);
            }
            return Result.OK();
        } catch (TransactionException e) {
            log.error("删除角色失败：{}", Arrays.toString(roleIds), e);
            return Result.error(e.getMessage());
        }


    }

}

