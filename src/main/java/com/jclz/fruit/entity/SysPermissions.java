package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 可视化后台用户权限
 */
@Component
@Data
public class SysPermissions {
    private int id;
    /**
     * 链接
     */
    private String url;
    /**
     * 权限描述---userInfo:view
     */
    private String description;
    /**
     * 权限名称---用户管理
     */
    private String name;
    /**
     * 父节点ID
     */
    private int parentId;
    /**
     * 是否锁定
     */
    private Boolean available = Boolean.FALSE;

    /**
     * 权限类型 1/菜单;2/按钮;3/接口
     */
    private int permType;

}
