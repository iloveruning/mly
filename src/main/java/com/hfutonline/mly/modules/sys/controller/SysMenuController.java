package com.hfutonline.mly.modules.sys.controller;


import com.hfutonline.mly.common.utils.Constant;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.sys.entity.SysMenu;
import com.hfutonline.mly.modules.sys.service.ISysMenuService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {


    private ISysMenuService menuService;

    @Autowired
    protected SysMenuController(ISysMenuService menuService) {
        this.menuService = menuService;
    }


    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public Result nav() {
        List<SysMenu> menuList = menuService.getUserMenus(ShiroKit.getUserId());
        return Result.OK().put("menuList", menuList);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list() {
        List<SysMenu> menuList = menuService.selectList(null);
        //目录
        List<SysMenu> catlog = new ArrayList<>();
        //菜单
        List<SysMenu> menu = new ArrayList<>();
        //按钮
        List<SysMenu> button = new ArrayList<>();

        for (SysMenu sysMenu : menuList) {
            if (sysMenu.getType() == Constant.MenuType.CATALOG.getValue()) {
                catlog.add(sysMenu);
            } else if (sysMenu.getType() == Constant.MenuType.MENU.getValue()) {
                menu.add(sysMenu);
            } else {
                button.add(sysMenu);
            }
        }

        for (SysMenu b : button) {
            for (SysMenu m : menu) {
                if (b.getPid().intValue() == m.getId().intValue()) {
                    b.setParentName(m.getName());
                    break;
                }
            }
        }

        for (SysMenu m : menu) {
            for (SysMenu c : catlog) {
                if (m.getPid().intValue() == c.getId().intValue()) {
                    m.setParentName(c.getName());
                    break;
                }
            }
        }

        //释放资源
        catlog = null;
        menu = null;
        button = null;

        System.out.println(menuList);

        return menuList;
    }

}

