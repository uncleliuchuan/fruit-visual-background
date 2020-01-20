package com.jclz.fruit.service;

import com.jclz.fruit.entity.Page;
import com.jclz.fruit.entity.SysRoles;

import java.util.List;
import java.util.Map;

public interface SysRoleService {


    List<SysRoles> queryRolesList();

    Page<SysRoles> queryRolesPage(String role, Integer current, Integer size);

    Map<String, Object> addRole(SysRoles role);

    Map<String, Object> deleteRole(Integer id);

    Map<String, Object> updateRole(SysRoles role);
}
