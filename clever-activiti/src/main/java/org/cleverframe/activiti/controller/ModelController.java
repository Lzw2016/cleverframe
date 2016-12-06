package org.cleverframe.activiti.controller;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.service.ModelService;
import org.cleverframe.activiti.vo.request.CreateModelVo;
import org.cleverframe.activiti.vo.request.DeployModelVo;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 23:36 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/activiti/model")
public class ModelController extends BaseController {

    @Autowired
    @Qualifier(ActivitiBeanNames.ModelService)
    private ModelService modelService;

    /**
     * 新建工作流程
     */
    @RequestMapping(value = "/createModel")
    @ResponseBody
    public AjaxMessage<Model> createModel(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CreateModelVo createModelVo,
            BindingResult bindingResult) {
        AjaxMessage<Model> message = new AjaxMessage<>(true, "创建流程模型成功", null);
        if (beanValidator(bindingResult, message)) {
            Model model = modelService.createModel(createModelVo, message);
            message.setResult(model);
        }
        return message;
    }

    /**
     * 根据数据库中的Model部署流程
     */
    @RequestMapping(value = "/deployModel")
    @ResponseBody
    public AjaxMessage<Deployment> deployModel(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DeployModelVo deployModelVo,
            BindingResult bindingResult) {
        AjaxMessage<Deployment> message = new AjaxMessage<>(true, "根据数据库中的Model部署流程成功", null);
        if (beanValidator(bindingResult, message)) {
            Deployment deployment = modelService.deployModel(deployModelVo.getModelId(), message);
            message.setResult(deployment);
        }
        return message;
    }
}
