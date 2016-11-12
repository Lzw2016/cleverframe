package org.cleverframe.sys.shiro;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.utils.ConversionUtils;
import org.cleverframe.common.vo.ValidateCode;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.attributes.SysSessionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 实现Shiro基于Form表单的身份验证过滤器，用于用户登录<br/>
 * 1.扩展org.apache.shiro.web.filter.authc.FormAuthenticationFilter的属性，增加验证码参数<br/>
 * 2.该类的实例对象会在spring-context-shiro.xml配置文件中注册，通过Spring容器可以获取该类对象<br/>
 * 3.在Spring容器中注册时需指定loginUrl、usernameParam、passwordParam、captchaParam、rememberMeParam属性<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 21:36 <br/>
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(LoginFormAuthenticationFilter.class);

    /**
     * 不使用验证码登录时，最多连续登录失败次数，失败次数大于此值就必须使用验证码登录，默认值：3次
     */
    private int loginFailedMaxCount = 3;

    /**
     * 验证码超时时间，默认值：60秒
     */
    private long validateCodeTimeout = 1000 * 60;

    /**
     * 用户登录表单的验证码参数名，Spring容器注册时需要指定该属性的值
     */
    private String captchaParam = "validateCode";

    /**
     * 处理登录表单中的参数，根据登录表单的参数生成 AuthenticationToken<br>
     *
     * @param request  htttp请求参数
     * @param response htpp响应参数
     * @return 返回用户提交的身份信息Toke，UserAuthorizingRealm 会使用此Toke验证用户是否合法
     * @see UserAuthorizingRealm
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        UserLoginToken userToken;
        // 获取用户登录的验证信息
        String userName = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = this.isRememberMe(request);
        String host = this.getHost(request);
        String captcha = request.getParameter(this.getCaptchaParam());
        if (password == null) {
            password = "";
        }
        // 根据用户验证信息构造Shiro验证Toke
        userToken = new UserLoginToken(userName, password.toCharArray(), rememberMe, host, captcha);
        logger.debug("用户登录请求Toke  " + userToken.toString());
        return userToken;
    }

    /**
     * 用户登录认证方法，当用户登录时调用<br>
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        AuthenticationToken token = null;
        try {
            token = createToken(request, response);
            if (token == null) {
                throw new UserLoginException(UserLoginException.System_Exception, "获取用户登录Token失败");
            }
            if (!(token instanceof UserLoginToken)) {
                throw new UserLoginException(UserLoginException.System_Exception, "Token转换UserLoginToken失败");
            }
            UserLoginToken userToken = (UserLoginToken) token;
            logger.debug("用户[{}]登录身份验证开始...", userToken.getUsername());
            // 用户登录认证，参考：super.executeLogin(request, response)
            if (!(request instanceof HttpServletRequest)) {
                throw new UserLoginException(UserLoginException.System_Exception, "ServletRequest转换HttpServletRequest失败");
            }

            HttpSession session = ((HttpServletRequest) request).getSession();

            // 登录次数过多必须使用验证码 - 验证验证码
            int loginFailedCount = ConversionUtils.converter(session.getAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT), 0);
            if (loginFailedCount >= this.loginFailedMaxCount) {
                ValidateCode captcha = (ValidateCode) session.getAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE);
                session.removeAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE);

                // 判断验证码存在
                if (captcha == null) {
                    throw new UserLoginException(UserLoginException.Not_Validate_Code, "连续登录失败次数过多，必须要使用验证码登录");
                }

                // 判断验证码是否超时
                if ((System.currentTimeMillis() - captcha.getCreateTime()) > validateCodeTimeout) {
                    throw new UserLoginException(UserLoginException.Validate_Code_Invalid, "验证码已超时");
                }

                // 判断验证码是否正确
                if (userToken.getCaptcha() == null || !userToken.getCaptcha().toUpperCase().equals(captcha.getContent())) {
                    throw new UserLoginException(UserLoginException.Validate_Code_Error, "验证码输入错误");
                }
            }
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (Exception e) {
            logger.error("用户登录认证-异常", e);
            AuthenticationException exception;
            if (e instanceof AuthenticationException) {
                exception = (AuthenticationException) e;
            } else {
                exception = new AuthenticationException(e);
            }
            return onLoginFailure(token, exception, request, response);
        }
    }

    /**
     * 用户登录验证失败回调方法<br>
     * 1.Session属性中用户连续登录失败次数加1<br>
     * 2.移除当前请求Session中的用户相关属性：<br>
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            if (!(request instanceof HttpServletRequest)) {
                throw new UserLoginException(UserLoginException.System_Exception, "ServletRequest转换HttpServletRequest失败");
            }
            HttpSession session = ((HttpServletRequest) request).getSession();
            // Session 属性中用户连续登录失败次数加 1
            Object temp = session.getAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT);
            int count = NumberUtils.toInt(temp == null ? null : temp.toString(), 0);
            count++;
            session.setAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT, count);
            // TODO 移除当前请求Session中的用户相关属性
            session.removeAttribute(SysSessionAttributes.LOGIN_USER);
            if (!(token instanceof UserLoginToken)) {
                throw new UserLoginException(UserLoginException.System_Exception, "Token转换UserLoginToken失败");
            }
            UserLoginToken userToken = (UserLoginToken) token;
            logger.debug("用户[{}]登录失败，连续登录失败次数：{} | 失败原因：{}", userToken.getUsername(), count, e.getMessage());
        } catch (Exception ex) {
            logger.error("用户登录认证-异常", ex);
        }
        return super.onLoginFailure(token, e, request, response);
    }

    /**
     * 设置登录失败信息<br>
     *
     * @param request 请求对象
     * @param ae      登录失败异常
     */
    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    /**
     * 用户登录验证成功回调方法<br>
     * 1.Session属性中用户连续登录失败次数清零<br>
     * 2.把User对象设置到Session属性中<br>
     * 3在当前请求Session中增加用户相关属性：<br>
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if (!(request instanceof HttpServletRequest)) {
            throw new UserLoginException(UserLoginException.System_Exception, "ServletRequest转换HttpServletRequest失败");
        }
        HttpSession session = ((HttpServletRequest) request).getSession();
        // Session属性中用户连续登录失败次数清零
        session.setAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT, 0);
        // 把当前用户加入到Session中
        Object object = subject.getPrincipal();
        if (!(object instanceof UserPrincipal)) {
            throw new UserLoginException(UserLoginException.System_Exception, "用户授权信息转换失败(UserPrincipal)");
        }
        UserPrincipal principal = (UserPrincipal) object;
        session.setAttribute(SysSessionAttributes.LOGIN_USER, principal.getUser());
        // TODO 把当前用户相关信息加入到Session中

        logger.debug("[{}]登录成功", principal.getUser().getLoginName());
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 登录成功之后跳转URL
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        // String url = getSuccessUrl();
        // WebUtils.issueRedirect(request, response, url, null, true);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "登入成功", null);
        ajaxMessage.setSuccessUrl(getSuccessUrl());
        String json = JacksonMapper.nonEmptyMapper().toJson(ajaxMessage);
        response.getWriter().print(json);
    }

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/

    public int getLoginFailedMaxCount() {
        return loginFailedMaxCount;
    }

    public void setLoginFailedMaxCount(int loginFailedMaxCount) {
        this.loginFailedMaxCount = loginFailedMaxCount;
    }

    public long getValidateCodeTimeout() {
        return validateCodeTimeout;
    }

    public void setValidateCodeTimeout(long validateCodeTimeout) {
        this.validateCodeTimeout = validateCodeTimeout;
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }
}
