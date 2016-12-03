package org.cleverframe.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 18:12 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/activiti/repository")
public class RepositoryController extends BaseController {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(RepositoryController.class);

    private ObjectMapper objectMapper = JacksonMapper.nonEmptyMapper().getMapper();

    @Autowired
    @Qualifier(SpringBeanNames.RepositoryService)
    private RepositoryService repositoryService;

    /**
     * ProcessDefinition 转换成 Model
     */
    @RequestMapping(value = "/convertToModel/{processDefinitionId}")
    @ResponseBody
    public AjaxMessage convertToModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        AjaxMessage message = new AjaxMessage(true, "部署的流程ProcessDefinition转换成Model成功", null);
        try {
            // 获取
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

            BpmnJsonConverter converter = new BpmnJsonConverter();
            ObjectNode modelNode = converter.convertToJson(bpmnModel);

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
            repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        } catch (Throwable e) {
            logger.error("", e);
            message.setSuccess(false);
            message.setFailMessage("ProcessDefinition转换Model失败");
        }
        return message;
    }
}
