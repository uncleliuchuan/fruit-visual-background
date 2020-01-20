package com.jclz.fruit.entity;

public class SysRolesPermissions {
    private long id;
    private long role_id;
    private long permission_id;

    public SysRolesPermissions() {
    }

    public SysRolesPermissions(long id, long role_id, long permission_id) {
        this.id = id;
        this.role_id = role_id;
        this.permission_id = permission_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setPermission_id(long permission_id) {
        this.permission_id = permission_id;
    }

    public long getPermission_id() {
        return permission_id;
    }
}
