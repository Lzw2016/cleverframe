package org.cleverframe.activiti.controller;

import org.cleverframe.activiti.ActivitiJspUrlPath;
import org.cleverframe.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/4 14:27 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/activiti/manager")
public class ActivitiManagerController extends BaseController {

    @RequestMapping("/Deployment" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDeploymentJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(ActivitiJspUrlPath.Deployment);
    }

    @RequestMapping("/Model" + VIEW_PAGE_SUFFIX)
    public ModelAndView getModelJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(ActivitiJspUrlPath.Model);
    }

    @RequestMapping("/ProcessDefinition" + VIEW_PAGE_SUFFIX)
    public ModelAndView getProcessDefinitionJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(ActivitiJspUrlPath.ProcessDefinition);
    }

    @RequestMapping("/ProcessInstance" + VIEW_PAGE_SUFFIX)
    public ModelAndView getProcessInstanceJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(ActivitiJspUrlPath.ProcessInstance);
    }

}
