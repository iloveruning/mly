package com.hfutonline.mly.modules.sys.controller;


import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.service.ISysUserService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/sys/user")
public class SysUserController {


    private ISysUserService userService;

    @Autowired
    protected SysUserController(ISysUserService userService){
        this.userService=userService;
    }

    /**
     * 所有用户列表
     */
    @SysLog("查看所有用户列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Result list(@RequestParam Map<String, Object> params){
        System.out.println(params);

        return Result.OK().put("page",userService.queryPage(params));
    }


    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public Result info(){
        return Result.OK().put("user", ShiroKit.getPrincipal());
    }

    /**
     * 用户信息
     */
    @SysLog("查看用户信息")
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Integer userId){

        SysUser user=userService.selectById(userId);

        //获取用户所属的角色列表
        List<Integer> roleIdList = userService.getUserRoleIds(userId);
        user.setRoleIdList(roleIdList);

        return Result.OK().put("user", user);
    }


    /**
     * 保存用户
     */
//    @SysLog("保存用户")
//    @PostMapping("/save")
//    @RequiresPermissions("sys:user:save")
//    public Result save(@RequestBody SysUser user){
//        ValidatorUtils.validateEntity(user, Add.class);
//
//        String salt = ShiroKit.getRandomSalt(16);
//        user.setSalt(salt);
//        String password = ShiroKit.md5(user.getPassword(), salt);
//        user.setPassword(password);
//
//
//        return R.ok();
//    }


    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Integer[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return Result.error("系统管理员不能删除");
        }

        if(ArrayUtils.contains(userIds, ShiroKit.getUserId())){
            return Result.error("当前用户不能删除");
        }

        userService.deleteBatchIds(Arrays.asList(userIds));

        return Result.OK();
    }
}

