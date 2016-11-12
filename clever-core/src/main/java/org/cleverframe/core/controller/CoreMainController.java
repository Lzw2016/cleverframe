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
 * 创建时间：2016-7-14 17:21 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/core/main")
public class CoreMainController extends BaseController {

    @RequestMapping("/CoreMain" + VIEW_PAGE_SUFFIX)
    public ModelAndView getCoreMainJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.CoreMain);
    }
}
