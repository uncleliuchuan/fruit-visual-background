package com.jclz.fruit.entity;

import lombok.Data;

@Data
public class SysUsersRoles {
    private Integer id;
    private Integer user_id;
    private Integer role_id;

    public SysUsersRoles(Integer user_id, Integer role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }
}
