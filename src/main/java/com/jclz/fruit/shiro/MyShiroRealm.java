package com.jclz.fruit.shiro;

import com.jclz.fruit.dao.SysPermissionMapper;
import com.jclz.fruit.dao.SysRoleMapper;
import com.jclz.fruit.entity.SysUser;
import com.jclz.fruit.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo = (SysUser) principals.getPrimaryPrincipal();
        sysRoleMapper.findRoleByUsername(userInfo.getUserName()).stream().forEach(
                sysRoles -> {
                    authorizationInfo.addRole(sysRoles.getRole());
                    sysPermissionMapper.findPermissionByRoleId(sysRoles.getId()).stream().forEach(
                            sysPermissions -> {
                                authorizationInfo.addStringPermission(sysPermissions.getDescription());
                            }
                    );
                }
        );
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
//        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
//        System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser userInfo = sysUserService.findByUserName(username);
//        System.out.println("----->>userInfo="+userInfo);
        if (userInfo == null) {
            return null;
        }
        if (userInfo.getLocked() == 1) { //账户冻结
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        if ("admin".equals(user.getUserName())) {
            user.setAdmin(true);
        }
        return user.isAdmin() || super.isPermitted(principals, permission);
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        if ("admin".equals(user.getUserName())) {
            user.setAdmin(true);
        }
        return user.isAdmin() || super.hasRole(principals, roleIdentifier);
    }
}