package org.cleverframe.sys.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 实现Shiro权限解析器，把权限字符串解析成Permission对象<br/>
 * 1.可参考org.apache.shiro.authz.permission.WildcardPermission<br/>
 * 2.该类的实例对象会在spring-context-shiro.xml配置文件中注册，通过Spring容器可以获取该类对象<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/10 22:47 <br/>
 */
public class UserPermissionResolver implements PermissionResolver {

    /**
     * 当Shiro调用isPermitted("user:view")认证权限信息时，调用此方法<br>
     * 1.若Shiro使用了缓存，并不是每次认证权限信息时都调用此方法<br>
     */
    @Override
    public Permission resolvePermission(String permissionString) {
        // TODO 把权限字符串解析成Permission对象，暂时参考WildcardPermissionResolver
        return new WildcardPermission(permissionString);
    }

}