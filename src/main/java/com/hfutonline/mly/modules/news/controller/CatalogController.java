package com.hfutonline.mly.modules.news.controller;

import com.hfutonline.mly.common.exception.ParamsException;
import com.hfutonline.mly.common.exception.TransactionException;
import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.common.validator.ValidatorUtils;
import com.hfutonline.mly.common.validator.group.Add;
import com.hfutonline.mly.common.validator.group.Update;
import com.hfutonline.mly.modules.news.entity.Catalog;
import com.hfutonline.mly.modules.news.service.CatalogService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 栏目
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@RestController
@RequestMapping("news/catalog")
public class CatalogController {

    private CatalogService catalogService;

    @Autowired
    protected  CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:catalog:list")
    public Result list(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        PageInfo page = catalogService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("news:catalog:info")
    public Result info(@PathVariable("id") Integer id) {
        Catalog catalog = catalogService.selectById(id);

        //获取栏目所属的标签列表
        List<Integer> tagIdList = catalogService.getCatalogTagIds(id);
        catalog.setTagIdList(tagIdList);
        return Result.OK().put("catalog", catalog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:catalog:save")
    public Result save(@RequestBody Catalog catalog) {

        try {
            ValidatorUtils.validateEntity(catalog, Add.class);
            catalog.setUsername(ShiroKit.getUserName());
            catalogService.save(catalog);
            return Result.OK();
        }catch (ParamsException e){
            return Result.error(HttpStatus.BAD_REQUEST,e.getMsg());
        }catch (TransactionException ee){
            return Result.error(ee.getMessage());
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:catalog:update")
    public Result update(@RequestBody Catalog catalog) {
        try {
            ValidatorUtils.validateEntity(catalog, Update.class);
            catalogService.update(catalog);
            return Result.OK();
        }catch (ParamsException e){
            return Result.error(HttpStatus.BAD_REQUEST,e.getMsg());
        }catch (TransactionException ee){
            return Result.error(ee.getMessage());
        }
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:catalog:delete")
    public Result delete(@RequestBody Integer[]ids) {
            catalogService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }

}
