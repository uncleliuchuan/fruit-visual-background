package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.entity.SysUser;
import com.jclz.fruit.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "用户api")
@RestController
@RequestMapping("/sys_user")
public class SysUserController {

    @Autowired
    SysUserService userService;

    /**
     * 用户列表
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "用户列表")
    @ResponseBody
    public Map<String, Object> queryUserList(@ApiParam(required = true, value = "用户") @RequestParam(value = "nick") String username,
                                             @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                             @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size) {
        Page<SysUser> sysUserList = userService.queryUserList(username, current, size);

        return GenResult.SUCCESS.genResult(sysUserList);
    }

    /**
     * 添加用户
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "添加用户")
    @ResponseBody
    public Map<String, Object> addUser(@ApiParam(required = true, value = "用户") SysUser user) {
        return userService.addUser(user);
    }

    /**
     * 更新用户信息
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "更新用户信息")
    @ResponseBody
    public Map<String, Object> updateUser(@ApiParam(required = true, value = "用户") SysUser user) {
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "删除用户")
    @ResponseBody
    public Map<String, Object> deleteUser(@ApiParam(required = true, value = "用户ID") @RequestParam(value = "id") Integer id) {
        return userService.deleteUser(id);
    }

    /**
     * 更新用户密码
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/pwd")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "更新用户密码")
    @ResponseBody
    public Map<String, Object> updateUserPwd(@ApiParam(required = true, value = "新密码") @RequestParam(value = "pwd") String password) {
        Subject subject = SecurityUtils.getSubject();
        SysUser userInfo = (SysUser) subject.getPrincipal();
        return userService.updateUserPwd(userInfo, password);
    }

    /**
     * 更新用户的角色
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/role")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "更新用户的角色")
    @ResponseBody
    public Map<String, Object> updateUserRole(@ApiParam(required = true, value = "用户ID") @RequestParam(value = "id") Integer id,
                                              @ApiParam(required = true, value = "角色ID列表") @RequestParam(value = "rids") Integer[] rids) {
        return userService.updateUserRole(id, rids);
    }
}
