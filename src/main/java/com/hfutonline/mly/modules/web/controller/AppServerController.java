package com.hfutonline.mly.modules.web.controller;

import com.hfutonline.mly.modules.web.service.AppServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 *
 * @author chenliangliang
 * @date 2018-02-25 21:25:16
 */
@RestController
@RequestMapping("web/appserver")
public class AppServerController {

    private AppServerService appServerService;

    @Autowired
    protected  AppServerController(AppServerService appServerService) {
        this.appServerService = appServerService;
    }






}
