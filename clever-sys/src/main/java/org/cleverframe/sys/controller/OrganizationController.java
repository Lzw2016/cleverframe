package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:42:15 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/sys/organization")
public class OrganizationController extends BaseController {

    @Autowired
    @Qualifier(SysBeanNames.OrganizationService)
    private OrganizationService organizationService;

    @RequestMapping("/Organization" + VIEW_PAGE_SUFFIX)
    public ModelAndView getOrganizationJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.Organization);
    }

}
