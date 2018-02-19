package com.hfutonline.mly.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfutonline.mly.common.utils.Constant;
import com.hfutonline.mly.modules.sys.entity.SysMenu;
import com.hfutonline.mly.modules.sys.mapper.SysMenuMapper;
import com.hfutonline.mly.modules.sys.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> getUserMenus(Integer userId) {

        List<SysMenu> menuList;
        if (userId == Constant.SUPER_ADMIN) {
            menuList = baseMapper.selectList(null);
        } else {
            menuList = baseMapper.queryUserMenus(userId);
        }
        return handleMenuList(menuList);
    }

    private List<SysMenu> handleMenuList(List<SysMenu> menuList) {

        //先找出目录
        List<SysMenu>  catalog=new ArrayList<>();
        Iterator<SysMenu> it = menuList.iterator();
        SysMenu menu;
        while (it.hasNext()){
            menu=it.next();
            if (menu.getType()==Constant.MenuType.CATALOG.getValue()){
                catalog.add(menu);
                it.remove();
            }else if (menu.getType()==Constant.MenuType.BUTTON.getValue()){
                it.remove();
            }
        }
        for (SysMenu c:catalog){
            List<SysMenu> subMenu=new ArrayList<>();
            for (SysMenu m:menuList){
                if (c.getId().intValue()==m.getPid().intValue()){
                    subMenu.add(m);
                }
            }
            c.setList(subMenu);
        }

        return catalog;
    }
}
