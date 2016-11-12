package org.cleverframe.sys.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Resources;
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
     * 验证用户是否有权访问<br/>
     *
     * @return 有权访问返回true，无权访问返回false
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (!(request instanceof HttpServletRequest)) {
            RuntimeException exception = new RuntimeException("请求ServletRequest不能转换成HttpServletRequest,授权失败");
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contextPath = httpRequest.getContextPath();
        String url = httpRequest.getRequestURI();
        url = StringUtils.removeStart(url, contextPath);
        Resources resources = userPermissionsService.getResources(url);
        if (resources == null) {
            logger.warn("请求用户[],请求地址[{}],授权失败，原因:资源未配置在资源表里", url);
            return false;
        }
        if (Resources.NO_NEED.equals(resources.getNeedAuthorization())) {
            logger.warn("请求用户[],请求地址[{}],授权成功允许访问，原因:资源不需要验证权限", url);
            return true;
        }
        String resourcesUrl = userPermissionsService.getResourcesKey(resources.getResourcesUrl());
        if (resourcesUrl == null || !url.equals(resourcesUrl.trim())) {
            logger.warn("请求用户[],请求地址[{}],授权失败，原因:资源表里的资源地址不正确({})", url, resourcesUrl);
            return false;
        }

        // 验证授权

        logger.debug("请求用户[],请求地址[{}],授权成功允许访问", url);
        return true;
    }
}
