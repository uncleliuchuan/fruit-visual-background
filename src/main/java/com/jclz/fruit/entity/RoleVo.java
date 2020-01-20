package com.jclz.fruit.entity;

import lombok.Data;

@Data
public class RoleVo {
    private String name;//显示名
    private String val;//值

    public RoleVo(String name, String val) {
        this.name = name;
        this.val = val;
    }
}
