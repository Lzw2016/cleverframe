package org.cleverframe.activiti.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.cleverframe.activiti.ActivitiBeanNames;
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

    /**
     * ProcessDefinition 转换成 Model
     */
    @Transactional(readOnly = false)
    public AjaxMessage processDefinitionConvertToModel(String processDefinitionId, AjaxMessage message) {
        try {
            // 获取 ProcessDefinition
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            // 创建 Model
            Model modelData = repositoryService.newModel();
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
        return message;
    }
}
