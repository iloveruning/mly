package com.hfutonline.mly.modules.web.controller;

import com.hfutonline.mly.common.utils.PageInfo;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.web.entity.Server;
import com.hfutonline.mly.modules.web.service.ServerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 服务器表
 *
 * @author chenliangliang
 * @date 2018-02-25 19:42:13
 */
@RestController
@RequestMapping("web/server")
public class ServerController {

    private ServerService serverService;

    @Autowired
    protected  ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("web:server:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageInfo page = serverService.queryPage(params);

        return Result.OK().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("web:server:info")
    public Result info(@PathVariable("id") Integer id) {
            Server server = serverService.selectById(id);

        return Result.OK().put("server", server);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("web:server:save")
    public Result save(@RequestBody Server server) {
            serverService.insert(server);

        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("web:server:update")
    public Result update(@RequestBody Server server) {
            serverService.updateById(server);

        return Result.OK();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("web:server:delete")
    public Result delete(@RequestBody Integer[]ids) {
            serverService.deleteBatchIds(Arrays.asList(ids));

        return Result.OK();
    }


    /**
     * 服务器列表
     */
    @GetMapping("/select")
    @RequiresPermissions("web:server:select")
    public Result select() {
        List<Server> list = serverService.getBaseInfo();

        return Result.OK().put("list", list);
    }

}
