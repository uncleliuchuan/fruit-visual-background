package com.jclz.fruit.dao;

import com.jclz.fruit.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("sysUserMapper")
public interface SysUserMapper {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public SysUser findByUserName(@Param("username") String username);

    /**
     * 查询用户列表
     *
     * @param username
     * @return
     */
    List<SysUser> queryUserList(@Param("username") String username,
                                @Param("start") Integer start,
                                @Param("size") Integer size);

    Integer queryUserListTotal(@Param("username") String username,
                               @Param("start") Integer start,
                               @Param("size") Integer size);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Integer addUser(SysUser user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    Integer updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    Integer deleteUser(Integer id);
}
