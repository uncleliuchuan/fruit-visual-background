package com.jclz.fruit.service;

import com.jclz.fruit.entity.SysPermissions;

import java.util.Map;

public interface SysPermissionsService {
    Map<String, Object> addPerm(SysPermissions perm);

    Map<String, Object> deletePerm(Integer id);

    Map<String, Object> updatePerm(SysPermissions perm);

    Map<String, Object> metaApiList();

    Map<String, Object> listAllPermissions();
}
