package com.jclz.fruit.service;

import com.jclz.fruit.entity.Page;
import com.jclz.fruit.entity.SysUser;

import java.util.Map;

public interface SysUserService {
    SysUser findByUserName(String username);

    Page<SysUser> queryUserList(String username, Integer current, Integer size);


    Map<String, Object> addUser(SysUser user);

    Map<String, Object> updateUser(SysUser user);

    Map<String, Object> deleteUser(Integer id);

    Map<String, Object> updateUserPwd(SysUser userInfo, String password);

    Map<String, Object> updateUserRole(Integer id, Integer[] rids);
}
