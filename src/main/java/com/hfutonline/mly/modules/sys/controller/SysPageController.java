package com.hfutonline.mly.modules.sys.controller;

import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *系统页面视图
 *
 * @author chenliangliang
 * @date 2018/2/18
 */
@Controller
public class SysPageController {

    @GetMapping("modules/{module}/{url}")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url){
        return "modules/" + module + "/" + url;
    }

    @GetMapping(value = {/*"/",*/ "index"})
    public String index(Model model){
        model.addAttribute("username", ShiroKit.getUserName());
        return "index";
    }

    @GetMapping("/upload")
    @RequiresPermissions("news:file:upload")
    public String upload(){
        return "modules/news/upload";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("main.html")
    public String main(){
        return "main";
    }

    @GetMapping("404.html")
    public String notFound(){
        return "404";
    }

}
