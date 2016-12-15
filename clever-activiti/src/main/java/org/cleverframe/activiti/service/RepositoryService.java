package org.cleverframe.activiti.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.repository.DeploymentResourceResponse;
import org.apache.commons.io.IOUtils;
import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.vo.request.GetDeploymentResourceDataVo;
import org.cleverframe.activiti.vo.request.GetDeploymentResourceVo;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/4 13:23 <br/>
 */
@Service(ActivitiBeanNames.RepositoryService)
public class RepositoryService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(RepositoryService.class);

    private ObjectMapper objectMapper = JacksonMapper.nonEmptyMapper().getMapper();

    @Autowired
    @Qualifier(SpringBeanNames.RepositoryService)
    private org.activiti.engine.RepositoryService repositoryService;

    @Autowired
    @Qualifier(SpringBeanNames.RestResponseFactory)
    protected RestResponseFactory restResponseFactory;

    @Autowired
    @Qualifier(SpringBeanNames.ContentTypeResolver)
    protected ContentTypeResolver contentTypeResolver;

    /**
     * 获取部署流程信息
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
    }

    /**
     * ProcessDefinition 转换成 Model
     */
    @Transactional(readOnly = false)
    public Model processDefinitionConvertToModel(String processDefinitionId, AjaxMessage message) {
        Model modelData = null;
        try {
            // 获取 ProcessDefinition
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            // 创建 Model
            modelData = repositoryService.newModel();
            modelData.setKey(processDefinition.getKey());
            modelData.setName(processDefinition.getResourceName());
            modelData.setCategory(processDefinition.getDeploymentId());
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
            modelData.setMetaInfo(modelObjectNode.toString());
            repositoryService.saveModel(modelData);
            // 创建 Model 编辑资源数据
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            BpmnJsonConverter converter = new BpmnJsonConverter();
            ObjectNode modelNode = converter.convertToJson(bpmnModel);
            repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
        } catch (Throwable e) {
            logger.error("", e);
            message.setSuccess(false);
            message.setFailMessage("ProcessDefinition转换Model失败");
        }
        return modelData;
    }

    /**
     * 获取部署资源<br/>
     * 因为activiti-rest模块的 “获取部署资源” 有Bug[ request.getPathInfo()返回null ]所以使用此方法代替<br/>
     * 参考: {@link org.activiti.rest.service.api.repository.DeploymentResourceResource} <br/>
     */
    public DeploymentResourceResponse getDeploymentResource(GetDeploymentResourceVo getDeploymentResourceVo, AjaxMessage message) {
        String deploymentId = getDeploymentResourceVo.getDeploymentId();
        String resourceName = getDeploymentResourceVo.getResourceName();
        // Check if deployment exists
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null) {
            message.setSuccess(false);
            message.setFailMessage("查询部署的流程不存在,部署流程ID: " + deploymentId);
        }
        List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);
        if (resourceList.contains(resourceName)) {
            // Build resource representation
            String contentType = contentTypeResolver.resolveContentType(resourceName);
            return restResponseFactory.createDeploymentResourceResponse(deploymentId, resourceName, contentType);
        } else {
            // Resource not found in deployment
            message.setSuccess(false);
            message.setFailMessage("查询部署资源不存在,部署流程ID: " + deploymentId + " ,资源名称: " + resourceName);
            return null;
        }
    }

    /**
     * 获取部署资源数据<br/>
     * 参考: {@link org.activiti.rest.service.api.repository.DeploymentResourceDataResource} <br/>
     */
    public byte[] getDeploymentResourceData(HttpServletResponse response, GetDeploymentResourceDataVo getDeploymentResourceDataVo, AjaxMessage message) {
        String deploymentId = getDeploymentResourceDataVo.getDeploymentId();
        String resourceId = getDeploymentResourceDataVo.getResourceId();
        // Check if deployment exists
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null) {
            message.setSuccess(false);
            message.setFailMessage("查询部署的流程不存在,部署流程ID: " + deploymentId);
            return null;
        }
        List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);
        if (!resourceList.contains(resourceId)) {
            message.setSuccess(false);
            message.setFailMessage("查询部署资源不存在,部署流程ID: " + deploymentId + " ,资源ID: " + resourceId);
            return null;
        }
        final InputStream resourceStream = repositoryService.getResourceAsStream(deploymentId, resourceId);
        String contentType = contentTypeResolver.resolveContentType(resourceId);
        response.setContentType(contentType);
        try {
            return IOUtils.toByteArray(resourceStream);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setFailMessage("转换数据流失败");
            return null;
        }
    }
}
