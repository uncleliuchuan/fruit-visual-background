package com.jclz.fruit.controller;

import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.SysPermissionMapper;
import com.jclz.fruit.dao.SysRoleMapper;
import com.jclz.fruit.entity.RoleVo;
import com.jclz.fruit.entity.SysPermissions;
import com.jclz.fruit.entity.SysRoles;
import com.jclz.fruit.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "shiro测试")
@RestController
@RequestMapping("/auth")
public class ShiroLoginController {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysPermissionMapper sysPermissionMapper;

    /**
     * 登录测试
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "登录测试")
    @ResponseBody
    public Map<String, Object> ajaxLogin(@ApiParam(required = true, value = "用户名") @RequestParam String username,
                                         @ApiParam(required = true, value = "密码") @RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            String sessionId = (String) subject.getSession().getId();
            jsonObject.put("token", sessionId);
            return GenResult.LOGIN_SUCCESS.genResult(jsonObject);
        } catch (IncorrectCredentialsException e) {
            return GenResult.PASSWORD_ERROR.genResult();
        } catch (LockedAccountException e) {
            return GenResult.USER_LOCKED_ERROR.genResult();
        } catch (AuthenticationException e) {
            return GenResult.USER_NOT_FOND.genResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GenResult.FAILED.genResult();
    }

    /**
     * 鉴权测试
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin")
    @ApiOperation(httpMethod = "GET", response = Map.class, responseContainer = "Map", value = "鉴权测试")
    public Map<String, Object> check() {
        return GenResult.SUCCESS.genResult("鉴权测试");
    }

    /**
     * 加载用户权限信息
     *
     * @return
     */
    @RequestMapping(value = "/info")
    @ApiOperation(httpMethod = "GET", response = Map.class, responseContainer = "Map", value = "加载用户权限信息")
    @ResponseBody
    public Map<String, Object> authLnfo() {
        Subject subject = SecurityUtils.getSubject();
        JSONObject jsonObject = new JSONObject();
        List<RoleVo> roles = new ArrayList<RoleVo>();
        List<RoleVo> permission = new ArrayList<RoleVo>();
        SysUser userInfo = (SysUser) subject.getPrincipal();
        RoleVo roleVo;
        List<SysRoles> rolesList = sysRoleMapper.findRoleByUsername(userInfo.getUserName());
        for (SysRoles role : rolesList) {
            roleVo = new RoleVo(role.getDescription(), role.getRole());
            roles.add(roleVo);
            List<SysPermissions> permissions = sysPermissionMapper.findPermissionByRoleId(role.getId());
            for (SysPermissions perm : permissions) {
                roleVo = new RoleVo(perm.getName(), perm.getDescription());
                permission.add(roleVo);
            }
        }
        jsonObject.put("roles", roles);
        jsonObject.put("perms", permission);
        jsonObject.put("nick", userInfo.getName());
        jsonObject.put("name", userInfo.getUserName());
        return GenResult.SUCCESS.genResult(jsonObject);
    }

    @PostMapping(value = "/logout")
    public Map<String, Object> logout() {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        return GenResult.SUCCESS.genResult();
    }
}