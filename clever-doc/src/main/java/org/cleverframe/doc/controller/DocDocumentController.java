package org.cleverframe.doc.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.doc.DocJspUrlPath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/12 22:29 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/doc/document")
public class DocDocumentController extends BaseController {

    @RequestMapping("/DocumentEdit" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDocumentEditJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(DocJspUrlPath.DocumentEdit);
    }

}
