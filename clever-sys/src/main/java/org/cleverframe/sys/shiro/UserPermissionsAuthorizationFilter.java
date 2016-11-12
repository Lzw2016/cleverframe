package org.cleverframe.sys.shiro;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.cleverframe.sys.SysBeanNames;
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
        String url = httpRequest.getRequestURI().replace(contextPath, "");





        logger.debug("请求用户[],请求地址[{}],授权成功允许访问", url);
        return true;
    }
}
