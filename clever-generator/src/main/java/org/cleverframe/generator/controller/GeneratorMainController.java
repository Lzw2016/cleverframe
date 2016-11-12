package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.generator.GeneratorJspUrlPath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-14 20:52 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/generator/main")
public class GeneratorMainController extends BaseController {

    @RequestMapping("/GeneratorMain" + VIEW_PAGE_SUFFIX)
    public ModelAndView getGeneratorMainJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(GeneratorJspUrlPath.GeneratorMain);
    }

}
