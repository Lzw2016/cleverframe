package org.cleverframe.sys.controller;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.imgvalidate.ImageValidateCageUtils;
import org.cleverframe.common.vo.ValidateCode;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.attributes.SysSessionAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * 用户登录请求地址
     */
    @RequestMapping("/userLogin")
    public void userLogin(){

    }
    /**
     * 用户登出
     */
    @RequestMapping("/userLogout")
    public void userLogout(){

    }
}
