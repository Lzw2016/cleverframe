//package org.cleverframe.shiro.shiro;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.LockedAccountException;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.cleverframe.common.utils.Encodes;
//import org.cleverframe.modules.sys.SysBeanNames;
//import org.cleverframe.modules.sys.entity.Organization;
//import org.cleverframe.modules.sys.entity.Permission;
//import org.cleverframe.modules.sys.entity.Role;
//import org.cleverframe.modules.sys.entity.User;
//import org.cleverframe.modules.sys.service.AuthorizingRealmService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
///**
// * 自定义的Shiro Realm实现，用于用户的登入认证和授权<br>
// * 1.参考 org.apache.shiro.realm.jdbc.JdbcRealm 实现<br>
// * 2.该类的实例对象会在spring-context-shiro.xml配置文件中注册，通过Spring容器可以获取该类对象<br>
// *
// * @author LiZhiWei
// * @version 2015年6月17日 下午9:45:28
// */
//public class ShiroAuthorizingRealm extends AuthorizingRealm
//{
//	//private final static Logger logger = LoggerFactory.getLogger(ShiroAuthorizingRealm.class);
//
//	@Autowired
//	@Qualifier(SysBeanNames.AuthorizingRealmService)
//	private AuthorizingRealmService authorizingRealmService;
//
//	public ShiroAuthorizingRealm()
//	{
//		super();
//	}
//
//	/**
//	 * Shiro认证时调用，到数据库获取认证信息<br>
//	 * 1.此方法的返回值AuthenticationInfo，当前对象的credentialsMatcher属性对象会使用此对象进行验证<br>
//	 * 2.当前对象的credentialsMatcher属性值在Spring注入是指定，其默认值：SimpleCredentialsMatcher<br>
//	 * 3.注意：在使用缓存的情况下，此方法并不是每次认证时都调用<br>
//	 *
//	 * @param token 用户提交的身份信息，如：用户名、密码
//	 * @return 返回Shiro的认证信息 AuthenticationInfo
//	 * @see org.apache.shiro.authc.credential.SimpleCredentialsMatcher
//	 * @see org.apache.shiro.authc.credential.HashedCredentialsMatcher
//	 * */
//	@Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
//    {
//        ShiroUserToken userToken = null;
//        if (token instanceof ShiroUserToken)
//        {
//            userToken = (ShiroUserToken) token;
//        }
//        else
//        {
//            return null;
//        }
//        // 获取用户信息，此处不用验证用户名密码是否正确，验证过程由AuthenticationInfo完成
//        User user = authorizingRealmService.findUserByLoginName(userToken.getUsername());
//        if (user == null)
//        {
//            throw new UnknownAccountException("没找到帐号");
//        }
//        else if (new Character(User.ACCOUNT_STATE_LOCKED).equals(user.getAccountState()))
//        {
//            throw new LockedAccountException("你的帐号已被锁定");
//        }
//        else if (new Character(User.ACCOUNT_STATE_DELETE).equals(user.getUserState()))
//        {
//            throw new LockedAccountException("此帐号已被删除");
//        }
//        else if (new Character(User.USER_STATE_LEAVE).equals(user.getUserState()))
//        {
//            throw new LockedAccountException("离职员工不能登录");
//        }
//        Organization homeCompany = authorizingRealmService.getOrgById(user.getHomeCompany());
//        Organization homeOrg = authorizingRealmService.getOrgById(user.getHomeOrg());
//        // user.getPassword()组成：前16存储密码的salt，16以后存储密码
//        byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
//        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(new ShiroPrincipal(homeCompany, homeOrg, user),// 用户信息
//                user.getPassword().substring(16),// 密码
//                new ShiroSimpleByteSource(salt),// salt
//                getName()// Realm name
//        );
//        return authenticationInfo;
//    }
//
//	/**
//	 * Shiro授权时调用的方法，到数据库获取获取授权信息<br>
//	 * 1.因为Shiro中可以同时配置多个Realm，所以身份信息可能就有多个，因此其提供了PrincipalCollection用于聚合这些身份信息<br>
//	 * 2.可参考 org.apache.shiro.realm.jdbc.JdbcRealm 获取所有角色信息，所有权限信息<br>
//	 * 3.注意：在使用缓存的情况下，此方法并不是每次认证时都调用<br>
//	 *
//	 * @param principals 身份信息
//	 * @return 返回Shiro的授权信息 AuthorizationInfo
//	 * */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
//	{
//		ShiroPrincipal principal = (ShiroPrincipal) getAvailablePrincipal(principals);
//		if(principal==null || principal.getUser()==null)
//		{
//			throw new AuthorizationException("授权失败，不能获取当前用户");
//		}
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		// 获取角色信息
//		List<Role> roleList = authorizingRealmService.findRoleByUserId(principal.getUser().getId());
//		List<Serializable> roleIdList=new ArrayList<Serializable>();
//		for (Role role : roleList)
//		{
//			info.addRole(role.getName());
//			roleIdList.add(role.getId());
//		}
//		// 获取权限信息
//		List<Permission> permissionList=authorizingRealmService.findPermissionByUserId(roleIdList);
//		for (Permission permission : permissionList)
//		{
//			info.addStringPermission(permission.getPermission());;
//		}
//		return info;
//	}
//}
