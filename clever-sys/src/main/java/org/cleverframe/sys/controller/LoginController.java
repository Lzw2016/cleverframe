package org.cleverframe.sys.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.imgvalidate.ImageValidateCageUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.vo.ValidateCode;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.attributes.SysSessionAttributes;
import org.cleverframe.sys.shiro.LoginFormAuthenticationFilter;
import org.cleverframe.sys.shiro.UserLoginException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 16:52 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/sys/login")
public class LoginController extends BaseController {

    @RequestMapping("/Login" + VIEW_PAGE_SUFFIX)
    public ModelAndView getLoginJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.Login);
    }

    /**
     * 获取图片验证码
     */
    @RequestMapping("/Validate.png")
    public void getImageValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String content = ImageValidateCageUtils.createImageStream(response.getOutputStream());
        if (StringUtils.isNotBlank(content)) {
            ValidateCode validateCode = new ValidateCode(System.currentTimeMillis(), content);
            request.getSession().setAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE, validateCode);
        } else {
            request.getSession().removeAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "生成验证码失败");
        }
    }

    /**
     * 用户登录请求地址  TODO 用户重复登入，必须判断用户是否想切换账号
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public AjaxMessage<String> userLogin(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "登入成功", null);
        LoginFormAuthenticationFilter loginFormAuthenticationFilter = SpringContextHolder.getBean(SpringBeanNames.LoginFormAuthenticationFilter);
        if (loginFormAuthenticationFilter == null) {
            throw new RuntimeException("获取注入Bean失败，BeanName=" + SpringBeanNames.LoginFormAuthenticationFilter);
        }

        // 验证用户已经登录
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            ajaxMessage.setSuccessUrl(loginFormAuthenticationFilter.getSuccessUrl());
            return ajaxMessage;
        }

        ajaxMessage.setSuccess(false);
        // 获取登入异常信息
        AuthenticationException authenticationException = (AuthenticationException) request.getAttribute(loginFormAuthenticationFilter.getFailureKeyAttribute());
        if (authenticationException == null) {
            throw new RuntimeException("获取登录异常信息失败");
        }
        ajaxMessage.setException(authenticationException);
        if (authenticationException instanceof UserLoginException) {
            UserLoginException userLoginException = (UserLoginException) authenticationException;
            ajaxMessage.setFailMessage(userLoginException.getMessage());
        } else {
            ajaxMessage.setFailMessage(authenticationException.getMessage());
        }

        // 获取登入失败次数 - 判断是否需要验证码
        Object temp = request.getSession().getAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT);
        int count = NumberUtils.toInt(temp == null ? null : temp.toString(), 0);
        if (count >= loginFormAuthenticationFilter.getLoginFailedMaxCount()) {
            ajaxMessage.setNeedValidateCode(true);
        }
        return ajaxMessage;
    }


//    /**
//     * 用户登出
//     */
//    @RequestMapping("/userLogout")
//    public void userLogout(){
//
//    }
}
