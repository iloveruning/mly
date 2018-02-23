package com.hfutonline.mly.modules.sys.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hfutonline.mly.common.annotation.SysLog;
import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.Constant;
import com.hfutonline.mly.common.utils.EhcacheTemplate;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.utils.ThreadExecutorUtil;
import com.hfutonline.mly.modules.sys.entity.SysMenu;
import com.hfutonline.mly.modules.sys.service.ISysMenuService;
import com.hfutonline.mly.modules.sys.shiro.ShiroRealm;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
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

    private ShiroRealm shiroRealm;

    private EhcacheTemplate cacheTemplate;

    private final String cacheName = "sysMenu";

    private final String prefix = "menu_";

    @Autowired
    protected SysMenuController(ISysMenuService menuService, ShiroRealm shiroRealm, EhcacheTemplate cacheTemplate) {
        this.menuService = menuService;
        this.shiroRealm = shiroRealm;
        this.cacheTemplate = cacheTemplate;
    }


    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public Result nav() throws Exception {
        Integer userId = ShiroKit.getUserId();
        return cacheTemplate.cacheable(cacheName, prefix + "nav_" + userId, () -> {
            List<SysMenu> menuList = menuService.getUserMenus(userId);
            return Result.OK().put("menuList", menuList);
        });

    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list() {
        List<SysMenu> menuList = menuService.selectList(new EntityWrapper<SysMenu>().orderBy("order_num", true));
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

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select() {
        //查询列表数据
        List<SysMenu> menuList = menuService.selectList(new EntityWrapper<SysMenu>().orderBy("order_num", true));

        Iterator<SysMenu> it = menuList.iterator();
        SysMenu menu;
        while (it.hasNext()) {
            menu = it.next();
            if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
                it.remove();
            }
        }
        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setId(0);
        root.setName("一级菜单");
        root.setPid(-1);
        root.setOpen(true);
        menuList.add(root);


        return Result.OK().put("menuList", menuList);
    }


    /**
     * 保存
     */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody SysMenu menu) {
        try {
            verifyMenu(menu);
            menuService.insert(menu);
            //清除权限缓存重新加载
            //PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
            //shiroRealm.clearCachedAuthorization(principals);
            //异步执行
            ThreadExecutorUtil.execute(shiroRealm::clearAllCachedAuthorizationInfo);
            return Result.OK();
        } catch (ParamsException e) {
            return Result.error(e.getMsg());
        }
    }


    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result info(@PathVariable("menuId") Integer menuId) {
        SysMenu menu = menuService.selectById(menuId);
        return Result.OK().put("menu", menu);
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result update(@RequestBody SysMenu menu) {
        try {
            //数据校验
            verifyMenu(menu);
            menuService.updateById(menu);
            return Result.OK();
        } catch (ParamsException e) {
            return Result.error(e.getMsg());
        }
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @PostMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public Result delete(@RequestParam("id") Integer menuId) {
       /* if (menuId <= 45) {
            return Result.error("系统菜单，不能删除");
        }*/


        //判断是否有子菜单或按钮
        int subMenu = menuService.selectCount(new EntityWrapper<SysMenu>()
                .eq("pid", menuId));
        if (subMenu > 0) {
            return Result.error("请先删除子菜单或按钮");
        }

        try {
            menuService.delete(menuId);
            return Result.OK();
        } catch (TransactionException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 验证参数是否正确
     */
    private void verifyMenu(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new ParamsException("菜单名称不能为空");
        }

        if (menu.getPid() == null || menu.getPid() < 0) {
            throw new ParamsException("上级菜单不能为空");
        }

        //目录
        if (menu.getType() == Constant.MenuType.CATALOG.getValue()) {
            menu.setPid(0);
            return;
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getPid() > 0) {
            SysMenu parentMenu = menuService.selectById(menu.getPid());
            if (parentMenu == null) {
                throw new ParamsException("上级菜单不存在");
            }
            parentType = parentMenu.getType();
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new ParamsException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new ParamsException("上级菜单只能为菜单类型");
            }
        }

    }


}

