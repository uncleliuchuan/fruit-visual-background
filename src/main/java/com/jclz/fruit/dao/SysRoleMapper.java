package com.jclz.fruit.dao;

import com.jclz.fruit.entity.SysRoles;
import com.jclz.fruit.entity.SysUsersRoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper {
    //通过username查找用户角色信息
    List<SysRoles> findRoleByUsername(@Param("username") String username);

    List<SysRoles> queryRolesList();

    Integer queryRolesPageTotal(String role, Integer start, Integer size);

    /**
     * 查询角色列表
     *
     * @param role
     * @return
     */
    List<SysRoles> queryRolesPage(@Param("role") String role,
                                  @Param("start") Integer start,
                                  @Param("size") Integer size);

    /**
     * 删除当前用户的旧角色
     *
     * @param userId
     * @return
     */
    Integer deleteOldRole(Integer userId);

    /**
     * 添加当前用户的角色
     *
     * @param list
     * @return
     */
    Integer addUserRole(List<SysUsersRoles> list);

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    Integer addRole(SysRoles role);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    Integer deleteRole(Integer id);

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    Integer updateRole(SysRoles role);
}
