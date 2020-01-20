package com.jclz.fruit.controller;

import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.entity.SysRoles;
import com.jclz.fruit.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "角色api")
@RestController
@RequestMapping("/sys_role")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    /**
     * 所有角色
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/option/role")
    @ApiOperation(httpMethod = "GET", response = Map.class, responseContainer = "Map", value = "所有角色")
    @ResponseBody
    public Map<String, Object> queryRoleList() {
        List<SysRoles> rolesList = sysRoleService.queryRolesList();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleList", rolesList);
        return GenResult.SUCCESS.genResult(jsonObject);
    }

    /**
     * 角色列表
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "角色列表")
    @ResponseBody
    public Map<String, Object> queryRoleList(@ApiParam(required = true, value = "用户") @RequestParam(value = "role") String role,
                                             @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                             @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size) {
        Page<SysRoles> rolesList = sysRoleService.queryRolesPage(role, current, size);

        return GenResult.SUCCESS.genResult(rolesList);
    }

    /**
     * 添加角色
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "添加角色")
    @ResponseBody
    public Map<String, Object> addRole(@ApiParam(required = true, value = "角色") SysRoles role) {

        return sysRoleService.addRole(role);
    }

    /**
     * 删除角色
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "删除角色")
    @ResponseBody
    public Map<String, Object> deleteRole(@ApiParam(required = true, value = "角色ID") @RequestParam("id") Integer id) {

        return sysRoleService.deleteRole(id);
    }

    /**
     * 修改角色信息
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "修改角色信息")
    @ResponseBody
    public Map<String, Object> updateRole(@ApiParam(required = true, value = "角色") SysRoles role) {

        return sysRoleService.updateRole(role);
    }

}