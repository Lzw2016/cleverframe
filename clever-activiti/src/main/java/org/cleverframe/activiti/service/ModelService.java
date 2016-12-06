package org.cleverframe.activiti.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.vo.request.CreateModelVo;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/4 13:34 <br/>
 */
@Service(ActivitiBeanNames.ModelService)
public class ModelService extends BaseService {

    @Autowired
    @Qualifier(SpringBeanNames.RepositoryService)
    private org.activiti.engine.RepositoryService repositoryService;

    @Autowired
    @Qualifier(SpringBeanNames.ObjectMapper)
    private ObjectMapper objectMapper;

    /**
     * 新建工作流程
     */
    @Transactional(readOnly = false)
    public Model createModel(CreateModelVo createModelVo, AjaxMessage message) {
        Model modelData = null;
        createModelVo.setDescription(StringUtils.defaultString(createModelVo.getDescription()));
        createModelVo.setKey(StringUtils.defaultString(createModelVo.getKey()));
        try {
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);

            modelData = repositoryService.newModel();
            modelData.setVersion(1);
            modelData.setName(createModelVo.getName());
            modelData.setKey(createModelVo.getKey());
            modelData.setCategory(createModelVo.getCategory());
            modelData.setTenantId(createModelVo.getTenantId());

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, createModelVo.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, createModelVo.getDescription());
            modelData.setMetaInfo(modelObjectNode.toString());
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (Throwable e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            message.setSuccess(false);
            message.setFailMessage("创建流程模型失败");
            message.setException(e);
        }
        return modelData;
    }

    /**
     * 根据数据库中的Model部署流程
     */
    @Transactional(readOnly = false)
    public Deployment deployModel(String modelId, AjaxMessage message) {
        Deployment deployment = null;
        Model modelData = repositoryService.getModel(modelId);
        try {
            final ObjectNode modelNode = (ObjectNode) objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processName = modelData.getName() + ".bpmn20.xml";
            deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes))
                    .deploy();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            message.setSuccess(false);
            message.setFailMessage("部署模型失败, Model ID:" + modelId);
            message.setException(e);
        }
        return deployment;
    }
}
