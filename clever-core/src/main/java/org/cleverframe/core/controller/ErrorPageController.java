package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.core.CoreJspUrlPath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/12 14:12 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/error/")
public class ErrorPageController extends BaseController {

    @RequestMapping("/403" + VIEW_PAGE_SUFFIX)
    public ModelAndView get403(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Error403);
    }

    @RequestMapping("/404" + VIEW_PAGE_SUFFIX)
    public ModelAndView get404(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Error404);
    }

    @RequestMapping("/500" + VIEW_PAGE_SUFFIX)
    public ModelAndView get500(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Error500);
    }

    @RequestMapping("/503" + VIEW_PAGE_SUFFIX)
    public ModelAndView get503(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Error503);
    }

    @RequestMapping("/KickOut" + VIEW_PAGE_SUFFIX)
    public ModelAndView getKickOut(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.KickOut);
    }
}
