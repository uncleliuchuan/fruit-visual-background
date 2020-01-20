package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.SysRoleMapper;
import com.jclz.fruit.dao.SysUserMapper;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.entity.SysRoles;
import com.jclz.fruit.entity.SysUser;
import com.jclz.fruit.entity.SysUsersRoles;
import com.jclz.fruit.service.SysUserService;
import com.jclz.fruit.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleMapper sysRoleService;

    @Override
    public SysUser findByUserName(String username) {
        return sysUserMapper.findByUserName(username);
    }

    @Override
    public Page<SysUser> queryUserList(String username, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<SysUser> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = sysUserMapper.queryUserListTotal(username, start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<SysUser> users = sysUserMapper.queryUserList(username, start, size);
        for (SysUser user : users) {
            List<SysRoles> roles = sysRoleService.findRoleByUsername(user.getUserName());
            user.setRoleList(roles);
        }
        page.setData(users);
        return page;
    }

    @Override
    @Transactional
    public Map<String, Object> addUser(SysUser user) {
        user.setSalt(user.getUserName());
        user.setPassword(MD5Util.encryptPassword(user.getPassword(), user.getUserName()));
        Integer row = sysUserMapper.addUser(user);
        if (row > 0) {
            return GenResult.SUCCESS.genResult(user);
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    @Transactional
    public Map<String, Object> updateUser(SysUser user) {
        user.setPassword(MD5Util.encryptPassword(user.getPassword(), user.getUserName()));
        Integer row = sysUserMapper.updateUser(user);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> deleteUser(Integer id) {
        Integer row = sysUserMapper.deleteUser(id);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    @Transactional
    public Map<String, Object> updateUserPwd(SysUser userInfo, String password) {
        userInfo.setPassword(MD5Util.encryptPassword(password, userInfo.getUserName()));
        Integer row = sysUserMapper.updateUser(userInfo);
        if (row > 0) {
            SecurityUtils.getSubject().logout();
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    @Transactional
    public Map<String, Object> updateUserRole(Integer userId, Integer[] rids) {
        sysRoleService.deleteOldRole(userId);
        List<SysUsersRoles> list = new ArrayList<>();
        for (Integer roleId : rids) {
            SysUsersRoles sysUsersRoles = new SysUsersRoles(userId, roleId);
            list.add(sysUsersRoles);
        }
        Integer row = sysRoleService.addUserRole(list);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }
}
