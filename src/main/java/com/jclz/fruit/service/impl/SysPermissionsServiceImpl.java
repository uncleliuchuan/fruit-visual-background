package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.SysPermissionMapper;
import com.jclz.fruit.entity.SysPermissions;
import com.jclz.fruit.service.SysPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysPermissionsService")
public class SysPermissionsServiceImpl implements SysPermissionsService {
    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    public Map<String, Object> addPerm(SysPermissions perm) {
        Integer row = sysPermissionMapper.addPerm(perm);
        if (row > 0) {
            return GenResult.SUCCESS.genResult(perm);
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> deletePerm(Integer id) {
        Integer row = sysPermissionMapper.deletePerm(id);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> updatePerm(SysPermissions perm) {
        Integer row = sysPermissionMapper.updatePerm(perm);
        if (row > 0) {
            return GenResult.SUCCESS.genResult(perm);
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> metaApiList() {
        List<SysPermissions> metaApiList = sysPermissionMapper.metaApiList();

        return null;
    }

    @Override
    public Map<String, Object> listAllPermissions() {
        return null;
    }
}
