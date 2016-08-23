//package org.cleverframe.shiro.shiro;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang3.math.NumberUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.cleverframe.common.attributes.SessionAttributes;
//import org.cleverframe.common.utils.ConversionUtils;
//import org.cleverframe.common.vo.ValidateCode;
//import org.cleverframe.modules.sys.attributes.SysSessionAttributes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 实现Shiro基于Form表单的身份验证过滤器，用于用户登入<br>
// * 1.扩展org.apache.shiro.web.filter.authc.FormAuthenticationFilter的属性，增加验证码参数<br>
// * 2.该类的实例对象会在spring-context-shiro.xml配置文件中注册，通过Spring容器可以获取该类对象<br>
// * 3.在Spring容器中注册时需指定loginUrl、usernameParam、passwordParam、captchaParam、rememberMeParam属性<br>
// *
// * @author LiZhiWei
// * @version 2015年6月17日 下午9:46:11
// */
//public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter
//{
//    /** 日志对象 */
//    private final static Logger logger = LoggerFactory.getLogger(ShiroFormAuthenticationFilter.class);
//
//    /** 不使用验证码登入时，最多连续登入失败次数，失败次数大于此值就必须使用验证码登入，默认值：3次 */
//    private int loginFailedMaxCount = 3;
//
//    /** 验证码超时时间，默认值：60秒 */
//    private long validateCodeTimeout = 1000 * 60;
//
//    /** 用户登录表单的验证码参数名，Spring容器注册时需要指定该属性的值 */
//    private String captchaParam = "validateCode";
//
//    /**
//     * 处理登录表单中的参数，根据登录表单的参数生成 AuthenticationToken<br>
//     *
//     * @param request htttp请求参数
//     * @param response htpp响应参数
//     * @return 返回用户提交的身份信息Toke，ShiroAuthorizingRealm 会使用此Toke验证用户是否合法
//     * @see ShiroAuthorizingRealm
//     * */
//    @Override
//    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)
//    {
//        ShiroUserToken userToken = null;
//        // 获取用户登入的验证信息
//        String userName = request.getParameter(this.getUsernameParam());
//        String password = request.getParameter(this.getPasswordParam());
//        boolean rememberMe = this.isRememberMe(request);
//        String host = this.getHost(request);
//        String captcha = request.getParameter(this.getCaptchaParam());
//        if (password == null)
//        {
//            password = "";
//        }
//        // 根据用户验证信息构造Shiro验证Toke
//        userToken = new ShiroUserToken(userName, password.toCharArray(), rememberMe, host, captcha);
//
//        // 调试日志
//        logger.debug(userToken.getUsername() + " | "
//                + userToken.getPassword().toString() + " | "
//                + userToken.isRememberMe() + " | "
//                + userToken.getHost() + " | "
//                + userToken.getCaptcha());
//        return userToken;
//    }
//
//    /**
//     * 用户登入认证方法，当用户登入时调用<br>
//     * */
//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception
//    {
//        // 调试日志
//        logger.debug("Shiro 登入授权认证中...");
//
//        AuthenticationToken token = createToken(request, response);
//        if (token == null)
//        {
//            throw new IllegalStateException("获取用户登入Token失败");
//        }
//
//        // 用户登入认证，参考：super.executeLogin(request, response)
//        try
//        {
//            // 获取请求HttpSession对象
//            if (!(request instanceof HttpServletRequest))
//            {
//                logger.error("ServletRequest转换HttpServletRequest失败");
//                throw new AuthenticationException("ServletRequest转换HttpServletRequest失败");
//            }
//            HttpSession session = ((HttpServletRequest) request).getSession();
//            ShiroUserToken userToken = (ShiroUserToken) token;
//            // 验证验证码
//            if (ConversionUtils.converter(session.getAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT), 0) >= this.loginFailedMaxCount)
//            {
//                ValidateCode captcha = (ValidateCode) session.getAttribute(SessionAttributes.VALIDATE_CODE);
//                session.removeAttribute(SessionAttributes.VALIDATE_CODE);
//                if(captcha == null)
//                {
//                    throw new CaptchaException("连续登入失败次数过多，必须要使用验证码登录");
//                }
//
//                // 判断验证码是否超时
//                if((System.currentTimeMillis() - captcha.getCreateTime()) > validateCodeTimeout)
//                {
//                    throw new CaptchaException("验证码已超时");
//                }
//
//                // 判断验证码是否正确
//                if (userToken.getCaptcha() == null || !userToken.getCaptcha().toUpperCase().equals(captcha.getContent()))
//                {
//                    throw new CaptchaException("验证码输入错误");
//                }
//            }
//            Subject subject = getSubject(request, response);
//            subject.login(token);
//            return onLoginSuccess(token, subject, request, response);
//        }
//        catch (AuthenticationException e)
//        {
//            return onLoginFailure(token, e, request, response);
//        }
//    }
//
//    /**
//     * 用户登入验证失败回调方法<br>
//     * 1.Session属性中用户连续登入失败次数加1<br>
//     * 2.移除当前请求Session中的用户相关属性：<br>
//     * */
//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response)
//    {
//        // 获取请求HttpSession对象
//        if (!(request instanceof HttpServletRequest))
//        {
//            logger.error("ServletRequest转换HttpServletRequest失败");
//            throw new AuthenticationException("ServletRequest转换HttpServletRequest失败");
//        }
//        HttpSession session = ((HttpServletRequest) request).getSession();
//        // Session属性中用户连续登入失败次数加1
//        Object temp = session.getAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT);
//        int count = NumberUtils.toInt(temp == null ? null : temp.toString(), 0);
//        count++;
//        session.setAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT, count);
//
//        // TODO 移除当前请求Session中的用户相关属性
//        session.removeAttribute(SysSessionAttributes.LOGIN_USER);
//        // 调试日志
//        logger.debug("登入失败，连续登入失败次数：" + count + "  失败原因：" + e.getMessage());
//        return super.onLoginFailure(token, e, request, response);
//    }
//
//    /**
//     * 设置登入失败信息<br>
//     * @param request 请求对象
//     * @param ae 登录失败异常
//     * */
//    @Override
//    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae)
//    {
//        String message = ae.getClass().getName();
//        if (ae instanceof CaptchaException)
//        {
//            message = ae.getMessage();
//        }
//        request.setAttribute(getFailureKeyAttribute(), message);
//    }
//
//    /**
//     * 用户登入验证成功回调方法<br>
//     * 1.Session属性中用户连续登入失败次数清零<br>
//     * 2.把User对象设置到Session属性中<br>
//     * 3在当前请求Session中增加用户相关属性：<br>
//     * */
//    @Override
//    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception
//    {
//        // 获取请求HttpSession对象
//        if (!(request instanceof HttpServletRequest))
//        {
//            logger.error("ServletRequest转换HttpServletRequest失败");
//            throw new AuthenticationException("ServletRequest转换HttpServletRequest失败");
//        }
//        HttpSession session = ((HttpServletRequest) request).getSession();
//        // Session属性中用户连续登入失败次数清零
//        session.setAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT, 0);
//        // 把当前用户加入到Session中
//        Object object = subject.getPrincipal();
//        if (!(object instanceof ShiroPrincipal))
//        {
//            logger.error("用户授权信息转换失败(ShiroPrincipal)");
//            throw new AuthenticationException("用户授权信息转换失败(ShiroPrincipal)");
//        }
//        ShiroPrincipal principal = (ShiroPrincipal) object;
//        session.setAttribute(SysSessionAttributes.LOGIN_USER, principal.getUser());
//        // TODO 把当前用户相关信息加入到Session中
//
//        // 调试日志
//        logger.debug("登入成功，登入名：" + principal.getUser().getLoginName());
//        return super.onLoginSuccess(token, subject, request, response);
//    }
//
//    /**
//     * 登录成功之后跳转URL
//     */
//    @Override
//    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception
//    {
//        String url = getSuccessUrl();
//        WebUtils.issueRedirect(request, response, url, null, true);
//        // super.issueSuccessRedirect(request, response);
//    }
//
//    /*--------------------------------------------------------------
//     * 			getter、setter
//     * -------------------------------------------------------------*/
//    public String getCaptchaParam()
//    {
//        return captchaParam;
//    }
//
//    public void setCaptchaParam(String captchaParam)
//    {
//        this.captchaParam = captchaParam;
//    }
//
//    public int getLoginFailedMaxCount()
//    {
//        return loginFailedMaxCount;
//    }
//
//    public void setLoginFailedMaxCount(int loginFailedMaxCount)
//    {
//        this.loginFailedMaxCount = loginFailedMaxCount;
//    }
//
//    public long getValidateCodeTimeout()
//    {
//        return validateCodeTimeout;
//    }
//
//    public void setValidateCodeTimeout(long validateCodeTimeout)
//    {
//        this.validateCodeTimeout = validateCodeTimeout;
//    }
//}
