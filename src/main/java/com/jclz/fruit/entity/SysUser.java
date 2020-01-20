package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 可视化后台用户
 */
@Component
@Data
public class SysUser implements Serializable {
    private int id;//

    private String userName;//用户名

    private String name;//昵称

    private String password;//密码

    private String salt;//salt=username+salt

    private String roleId;//权限id

    private int locked;//用户状态 --0/正常 --1/冻结

    private boolean isAdmin;

    private List<SysRoles> roleList;


}
