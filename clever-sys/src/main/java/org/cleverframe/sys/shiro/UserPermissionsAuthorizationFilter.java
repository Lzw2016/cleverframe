package org.cleverframe.sys.shiro;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.service.IUserPermissionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限授权拦截器，验证用户是否拥有所有权限
 * 作者：LiZW <br/>
 * 创建时间：2016/11/10 21:48 <br/>
 *
 * @see org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
 */
public class UserPermissionsAuthorizationFilter extends AuthorizationFilter {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(UserPermissionsAuthorizationFilter.class);

    @Autowired
    @Qualifier(SysBeanNames.EhCacheResourcesService)
    private IUserPermissionsService userPermissionsService;

    /**
     * 移除Url后缀(如：.css、.js、.html、.json、.xml等)
     *
     * @param url 带有后缀的Url
     * @return 无后缀的Url
     */
    public String removeUrlSuffix(String url) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        int positionBySeparator = url.lastIndexOf("/");
        int positionPoint = url.lastIndexOf(".");
        if (positionPoint > positionBySeparator && positionPoint >= 0) {
            url = url.substring(0, positionPoint);
        }
        return url.trim();
    }

    /**
     * 验证用户是否有权访问<br/>
     *
     * @return 有权访问返回true，无权访问返回false
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("请求ServletRequest不能转换成HttpServletRequest,授权失败");
        }
        // 解析当前请求url地址
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contextPath = httpRequest.getContextPath();
        String url = httpRequest.getRequestURI();
        url = StringUtils.removeStart(url, contextPath);
        String urlNoSuffix = removeUrlSuffix(url);

        // 获取当前登录用户信息
        Subject subject = getSubject(request, response);
        Object object = subject.getPrincipal();
        if (!(object instanceof UserPrincipal)) {
            throw new RuntimeException("当前登录用户对象不能转换成UserPrincipal对象");
        }
        UserPrincipal userPrincipal = (UserPrincipal) object;
        User user = userPrincipal.getUser();
        if (user == null) {
            logger.warn("请求地址[{}],授权失败，原因:获取用户登录信息为空", url);
            return false;
        }

        // 获取当前url在数据库里配置的授权信息 - 验证授权
        Resources resources = userPermissionsService.getResourcesByCache(url);
        if (resources == null) {
            resources = userPermissionsService.getResourcesByCache(urlNoSuffix);
            if (resources == null) {
                logger.warn("请求用户[{}],请求地址[{}],授权失败，原因:资源未配置在资源表里", user.getLoginName(), url);
                return false;
            }
        }
        if (Resources.NO_NEED.equals(resources.getNeedAuthorization())) {
            logger.warn("请求用户[{}],请求地址[{}],授权成功允许访问，原因:资源不需要验证权限", user.getLoginName(), url);
            return true;
        }
        String resourcesUrl = userPermissionsService.getResourcesKey(resources.getResourcesUrl());
        if (resourcesUrl == null || !urlNoSuffix.equals(removeUrlSuffix(resourcesUrl))) {
            logger.warn("请求用户[{}],请求地址[{}],授权失败，原因:资源表里的资源地址不正确({})", user.getLoginName(), url, resourcesUrl);
            return false;
        }
        // 验证授权 - mappedValue
        String[] perms = (String[]) mappedValue;
        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
            if (!isPermitted) {
                logger.warn("请求用户[{}],请求地址[{}],授权失败，原因:没有权限[mappedValue={}]", user.getLoginName(), url, ArrayUtils.toString(mappedValue));
                return false;
            }
        }
        // 验证权限 - 自定义Url权限字符串
        isPermitted = subject.isPermitted(resources.getPermission());
        if (isPermitted) {
            logger.debug("请求用户[{}],请求地址[{}],授权成功允许访问。验证权限[urlPermission={} ,mappedValue={}]",
                    user.getLoginName(),
                    url,
                    resources.getPermission(),
                    ArrayUtils.toString(mappedValue));
        } else {
            logger.warn("请求用户[{}],请求地址[{}],授权失败，原因:没有权限[urlPermission={}]", user.getLoginName(), url, resources.getPermission());
        }
        return isPermitted;
    }
}
