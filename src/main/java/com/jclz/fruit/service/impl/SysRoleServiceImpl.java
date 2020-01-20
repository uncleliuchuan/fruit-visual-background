package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.SysRoleMapper;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.entity.SysRoles;
import com.jclz.fruit.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoles> queryRolesList() {
        return sysRoleMapper.queryRolesList();
    }

    @Override
    public Page<SysRoles> queryRolesPage(String role, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<SysRoles> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = sysRoleMapper.queryRolesPageTotal(role, start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<SysRoles> roleList = sysRoleMapper.queryRolesPage(role, start, size);
        page.setData(roleList);
        return page;
    }

    @Override
    public Map<String, Object> addRole(SysRoles role) {
        Integer row = sysRoleMapper.addRole(role);
        if (row > 0) {
            return GenResult.SUCCESS.genResult(role);
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> deleteRole(Integer id) {
        Integer row = sysRoleMapper.deleteRole(id);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> updateRole(SysRoles role) {
        Integer row = sysRoleMapper.updateRole(role);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }
}
