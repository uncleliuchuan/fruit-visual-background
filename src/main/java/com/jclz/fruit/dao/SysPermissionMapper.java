package com.jclz.fruit.dao;

import com.jclz.fruit.entity.SysPermissions;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper {
    //根据角色ID查询角色对应的权限信息
    List<SysPermissions> findPermissionByRoleId(@Param("roleId") Integer roleId);

    /**
     * 添加权限
     *
     * @param perm
     * @return
     */
    Integer addPerm(SysPermissions perm);

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    Integer deletePerm(Integer id);

    /**
     * 更新权限
     *
     * @param perm
     * @return
     */
    Integer updatePerm(SysPermissions perm);

    /**
     * 查询接口权限元数据
     *
     * @return
     */
    List<SysPermissions> metaApiList();
}
