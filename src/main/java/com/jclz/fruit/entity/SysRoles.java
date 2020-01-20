package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 可视化后台用户角色
 */
@Component
@Data
public class SysRoles {
    private int id;
    /**
     * 角色名称
     */
    private String role;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 是否锁定
     */
    private Boolean available = Boolean.FALSE;

}
