package org.cleverframe.activiti.controller;

import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.service.api.repository.DeploymentResourceResponse;
import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.service.RepositoryService;
import org.cleverframe.activiti.vo.request.GetDeploymentResourceDataVo;
import org.cleverframe.activiti.vo.request.GetDeploymentResourceVo;
import org.cleverframe.activiti.vo.response.ProcessDefinitionVo;
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
    public AjaxMessage<Model> convertToModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        AjaxMessage<Model> message = new AjaxMessage<>(true, "部署的流程ProcessDefinition转换成Model成功", null);
        Model model = repositoryService.processDefinitionConvertToModel(processDefinitionId, message);
        if (model != null) {
            message.setResult(model);
        }
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

    /**
     * 获取部署流程信息
     */
    @RequestMapping(value = "/getProcessDefinition/{processDefinitionId}")
    @ResponseBody
    public AjaxMessage<ProcessDefinitionVo> getProcessDefinition(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(required = true, name = "processDefinitionId") String processDefinitionId) {
        AjaxMessage<ProcessDefinitionVo> message = new AjaxMessage<>(true, "获取部署流程信息成功", null);
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        if (processDefinition == null) {
            message.setSuccess(false);
            message.setFailMessage("获取部署流程信息失败");
        } else {
            ProcessDefinitionVo processDefinitionVo = new ProcessDefinitionVo();
            processDefinitionVo.setId(processDefinition.getId());
            processDefinitionVo.setCategory(processDefinition.getCategory());
            processDefinitionVo.setName(processDefinition.getName());
            processDefinitionVo.setKey(processDefinition.getKey());
            processDefinitionVo.setDescription(processDefinition.getDescription());
            processDefinitionVo.setVersion(processDefinition.getVersion());
            processDefinitionVo.setResourceName(processDefinition.getResourceName());
            processDefinitionVo.setDeploymentId(processDefinition.getDeploymentId());
            processDefinitionVo.setDiagramResourceName(processDefinition.getDiagramResourceName());
            processDefinitionVo.setStartFormDefined(processDefinition.hasStartFormKey());
            processDefinitionVo.setGraphicalNotationDefined(processDefinition.hasGraphicalNotation());
            processDefinitionVo.setSuspended(processDefinition.isSuspended());
            processDefinitionVo.setTenantId(processDefinition.getTenantId());
            message.setResult(processDefinitionVo);
        }
        return message;
    }
}
