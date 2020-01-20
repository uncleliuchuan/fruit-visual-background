package com.jclz.fruit.controller;

import com.jclz.fruit.entity.SysPermissions;
import com.jclz.fruit.service.SysPermissionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "权限api")
@RestController
@RequestMapping("/sys_perm")
public class SysPermissionsController {

    @Autowired
    SysPermissionsService sysPermissionsService;

    /**
     * 添加权限
     *
     * @param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "添加权限")
    @ResponseBody
    public Map<String, Object> addPerm(@ApiParam(required = true, value = "权限") SysPermissions perm) {
        return sysPermissionsService.addPerm(perm);
    }

    /**
     * 删除权限
     *
     * @param
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "删除权限")
    @ResponseBody
    public Map<String, Object> deletePerm(@ApiParam(required = true, value = "权限ID") Integer id) {
        return sysPermissionsService.deletePerm(id);
    }

    /**
     * 更新权限
     *
     * @param
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "更新权限")
    @ResponseBody
    public Map<String, Object> updatePerm(@ApiParam(required = true, value = "权限") SysPermissions perm) {
        return sysPermissionsService.updatePerm(perm);
    }

    /**
     * 查询接口权限元数据
     *
     * @param
     * @return
     */
    @PostMapping("/meta/api")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "查询接口权限元数据")
    @ResponseBody
    public Map<String, Object> metaApiList() {
        return sysPermissionsService.metaApiList();
    }

    /**
     * 查询接口权限元数据
     *
     * @param
     * @return
     */
    @GetMapping("/list/all")
    @ApiOperation(httpMethod = "GET", response = Map.class, responseContainer = "Map", value = "列出所有菜单、按钮、接口等权限")
    @ResponseBody
    public Map<String, Object> listAllPermissions() {
        return sysPermissionsService.listAllPermissions();
    }
}
