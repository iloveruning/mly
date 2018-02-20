package com.hfutonline.mly.modules.sys.controller;


import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.validator.ValidatorUtils;
import com.hfutonline.mly.common.validator.group.Add;
import com.hfutonline.mly.common.validator.group.Update;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.service.ISysUserService;
import com.hfutonline.mly.modules.sys.shiro.ShiroUser;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController {


    private ISysUserService userService;

    @Autowired
    protected SysUserController(ISysUserService userService) {
        this.userService = userService;
    }

    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        System.out.println(params);

        return Result.OK().put("page", userService.queryPage(params));
    }


    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public Result info() {
        return Result.OK().put("user", ShiroKit.getPrincipal());
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Integer userId) {

        SysUser user = userService.selectById(userId);

        //获取用户所属的角色列表
        List<Integer> roleIdList = userService.getUserRoleIds(userId);
        user.setRoleIdList(roleIdList);

        return Result.OK().put("user", user);
    }


    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUser user) {

        try {
            ValidatorUtils.validateEntity(user, Add.class);
            userService.save(user);
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
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody SysUser user) {

        try {
            ValidatorUtils.validateEntity(user, Update.class);
            userService.update(user);
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
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Integer[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return Result.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, ShiroKit.getUserId())) {
            return Result.error("当前用户不能删除");
        }

        userService.deleteBatchIds(Arrays.asList(userIds));

        return Result.OK();
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    @RequiresUser
    public Result password(@RequestParam("old") String password, @RequestParam("new")String newPassword){


        if (StringUtils.isBlank(password)||StringUtils.isBlank(newPassword)){
            return Result.error(HttpStatus.BAD_REQUEST,"密码不能为空");
        }

        ShiroUser user=ShiroKit.getPrincipal();
        //原密码
        password = ShiroKit.md5(password,user.getSalt());
        //新密码
        newPassword = ShiroKit.md5(newPassword,user.getSalt());

        //更新密码
        boolean flag = userService.updatePassword(user.getId(), password, newPassword);
        if(!flag){
            return Result.error("原密码不正确");
        }

        return Result.OK();
    }
}

