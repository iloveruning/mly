package com.hfutonline.mly.modules.sys.controller;


import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.sys.service.ISysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    private ISysLogService logService;

    @Autowired
    protected SysLogController(ISysLogService logService){
        this.logService=logService;
    }

    /**
     * 列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    public Result list(@RequestParam Map<String, Object> params){
        System.out.println(params);
        return Result.OK().put("page",logService.queryPage(params));
    }

}

