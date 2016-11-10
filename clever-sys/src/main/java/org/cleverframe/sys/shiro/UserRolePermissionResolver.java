package org.cleverframe.sys.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;

import java.util.Collection;

/**
 * 实现Shiro的角色解析器，根据角色字符串解析出角色所有的权限<br/>
 * 1.该类的实例对象会在spring-context-shiro.xml配置文件中注册，通过Spring容器可以获取该类对象<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/10 22:49 <br/>
 */
public class UserRolePermissionResolver implements RolePermissionResolver {

    /**
     * 当Shiro调用isPermitted("user:view")认证权限信息时，调用此方法<br>
     * 1.若Shiro使用了缓存，并不是每次认证权限信息时都调用此方法<br>
     */
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        // TODO 根据角色字符串解析出角色所有的权限
        return null;
    }

}