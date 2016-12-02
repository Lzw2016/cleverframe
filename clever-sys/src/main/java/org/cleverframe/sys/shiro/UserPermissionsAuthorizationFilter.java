package org.cleverframe.sys.shiro;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.service.IUserPermissionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

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
     * 根据请求对象获取 Spring Controller 里对应的方法名称
     *
     * @return 资源不存在(404) 返回 null
     */
    private String getHandlerMethod(HttpServletRequest request) throws Exception {
        RequestMappingHandlerMapping handlerMapping = SpringContextHolder.getWebBean(RequestMappingHandlerMapping.class);
        if (handlerMapping == null) {
            throw new RuntimeException("获取RequestMappingHandlerMapping失败,权限验证异常");
        }
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
        if (handlerExecutionChain == null) {
            return null;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();
        return handlerMethod.getBeanType().getName() + "#" + handlerMethod.getMethod().getName();
    }

    /**
     * 输出授权结构日志信息
     *
     * @param isSuccess   是否成功
     * @param message     授权原因信息
     * @param url         请求url
     * @param urlNoSuffix 请求地址(无后缀)
     * @param user        当前用户
     * @param resources   资源
     */
    private void printLog(boolean isSuccess, String message, String url, String urlNoSuffix, User user, Resources resources) {
        // 审核失败
        if ((!isSuccess || user == null) && logger.isWarnEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# 授权失败: " + message + "\r\n";
            if (user != null) {
                tmp = tmp + "# 请求用户: " + user.getLoginName() + "\r\n";
            }
            tmp = tmp +
                    "# 请求地址: " + url + "\r\n" +
                    "# 请求地址(无后缀): " + urlNoSuffix + "\r\n";
            if (resources != null) {
                tmp = tmp +
                        "# 请求地址(Controller配置): " + resources.getResourcesUrl() + "\r\n" +
                        "# 请求方法: " + resources.getControllerMethod() + "\r\n" +
                        "# 权限标识字符串: " + resources.getPermission() + "\r\n";
            }
            tmp = tmp +
                    "#=======================================================================================================================#\r\n";
            logger.warn(tmp);
        }
        // 审核成功
        if (isSuccess && logger.isDebugEnabled()) {
            assert user != null;
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# 授权成功允许访问: " + message + "\r\n" +
                    "# 请求用户: " + user.getLoginName() + "\r\n" +
                    "# 请求地址: " + url + "\r\n" +
                    "# 请求地址(无后缀): " + urlNoSuffix + "\r\n" +
                    "# 请求地址(Controller配置): " + resources.getResourcesUrl() + "\r\n" +
                    "# 请求方法: " + resources.getControllerMethod() + "\r\n" +
                    "# 权限标识字符串: " + resources.getPermission() + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.debug(tmp);
        }
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
        String fullMethodName = getHandlerMethod(httpRequest);
        if (fullMethodName == null) {
            // TODO 此处应该抛出 404 资源不存在
            printLog(false, "未匹配到映射Controller的方法", url, urlNoSuffix, null, null);
            return true;
        }

        // 获取当前登录用户信息
        Subject subject = getSubject(request, response);
        Object object = subject.getPrincipal();
        if (!(object instanceof UserPrincipal)) {
            throw new RuntimeException("当前登录用户对象不能转换成UserPrincipal对象");
        }
        UserPrincipal userPrincipal = (UserPrincipal) object;
        User user = userPrincipal.getUser();
        if (user == null) {
            printLog(false, "获取用户登录信息为空", url, urlNoSuffix, null, null);
            return false;
        }

        // 获取当前url在数据库里配置的授权信息 - 验证授权
        Resources resources = userPermissionsService.getResourcesByMethod(fullMethodName);
        if (resources == null) {
            printLog(false, "资源未配置在资源表里", url, urlNoSuffix, user, null);
            return false;
        }
        if (Resources.NO_NEED.equals(resources.getNeedAuthorization())) {
            printLog(true, "资源不需要验证权限", url, urlNoSuffix, user, resources);
            return true;
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
                printLog(false, "没有授权=" + ArrayUtils.toString(mappedValue), url, urlNoSuffix, user, resources);
                return false;
            }
        }

        // 验证权限 - 自定义Url权限字符串
        isPermitted = subject.isPermitted(resources.getPermission());
        if (isPermitted) {
            printLog(true, "授权成功=" + ArrayUtils.toString(mappedValue), url, urlNoSuffix, user, resources);
        } else {
            printLog(false, "没有授权=" + ArrayUtils.toString(mappedValue), url, urlNoSuffix, user, resources);
        }
        return isPermitted;
    }
}
