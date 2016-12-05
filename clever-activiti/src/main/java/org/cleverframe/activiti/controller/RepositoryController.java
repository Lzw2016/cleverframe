package org.cleverframe.activiti.controller;

import org.activiti.rest.service.api.repository.DeploymentResourceResponse;
import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.service.RepositoryService;
import org.cleverframe.activiti.vo.request.GetDeploymentResourceDataVo;
import org.cleverframe.activiti.vo.request.GetDeploymentResourceVo;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 18:12 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/activiti/repository")
public class RepositoryController extends BaseController {

    @Autowired
    @Qualifier(ActivitiBeanNames.RepositoryService)
    private RepositoryService repositoryService;

    /**
     * ProcessDefinition 转换成 Model
     */
    @RequestMapping(value = "/convertToModel/{processDefinitionId}")
    @ResponseBody
    public AjaxMessage convertToModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        AjaxMessage message = new AjaxMessage(true, "部署的流程ProcessDefinition转换成Model成功", null);
        message = repositoryService.processDefinitionConvertToModel(processDefinitionId, message);
        return message;
    }

    /**
     * 获取部署资源<br/>
     * 因为activiti-rest模块的 “获取部署资源” 有Bug[ request.getPathInfo()返回null ]所以使用此方法代替<br/>
     * 参考: {@link org.activiti.rest.service.api.repository.DeploymentResourceResource} <br/>
     */
    @RequestMapping(value = "/getDeploymentResource")
    @ResponseBody
    public AjaxMessage<DeploymentResourceResponse> getDeploymentResource(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid GetDeploymentResourceVo getDeploymentResourceVo,
            BindingResult bindingResult) {
        AjaxMessage<DeploymentResourceResponse> message = new AjaxMessage<>(true, "获取部署资源成功", null);
        if (beanValidator(bindingResult, message)) {
            DeploymentResourceResponse resourceResponse = repositoryService.getDeploymentResource(getDeploymentResourceVo, message);
            message.setResult(resourceResponse);
        }
        return message;
    }

    /**
     * 获取部署资源数据<br/>
     * 参考: {@link org.activiti.rest.service.api.repository.DeploymentResourceDataResource} <br/>
     */
    @RequestMapping(value = "/getDeploymentResourceData")
    public void getDeploymentResourceData(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid GetDeploymentResourceDataVo getDeploymentResourceDataVo,
            BindingResult bindingResult) throws IOException {
        AjaxMessage message = new AjaxMessage(true, "获取部署资源成功", null);
        if (beanValidator(bindingResult, message)) {
            byte[] data = repositoryService.getDeploymentResourceData(response, getDeploymentResourceDataVo, message);
            if (data != null) {
                response.getOutputStream().write(data);
                return;
            }
        }
        response.setContentType("application/json");
        response.getOutputStream().write(JacksonMapper.nonEmptyMapper().toJson(message).getBytes());
    }
}
