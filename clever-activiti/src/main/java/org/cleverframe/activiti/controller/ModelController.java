package org.cleverframe.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.activiti.vo.request.CreateModelVo;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.spring.SpringBeanNames;
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

    private ObjectMapper objectMapper = JacksonMapper.nonEmptyMapper().getMapper();

    @Autowired
    @Qualifier(SpringBeanNames.RepositoryService)
    private RepositoryService repositoryService;

    @RequestMapping(value = "/createModel")
    @ResponseBody
    public AjaxMessage<Model> createModel(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CreateModelVo createModelVo,
            BindingResult bindingResult) {
        AjaxMessage<Model> message = new AjaxMessage<>(true, "创建流程模型成功", null);
        if (beanValidator(bindingResult, message)) {
            createModelVo.setDescription(StringUtils.defaultString(createModelVo.getDescription()));
            createModelVo.setKey(StringUtils.defaultString(createModelVo.getKey()));
            try {
                ObjectNode editorNode = objectMapper.createObjectNode();
                editorNode.put("id", "canvas");
                editorNode.put("resourceId", "canvas");
                ObjectNode stencilSetNode = objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
                editorNode.set("stencilset", stencilSetNode);

                Model modelData = repositoryService.newModel();
                modelData.setVersion(1);
                modelData.setName(createModelVo.getName());
                modelData.setKey(createModelVo.getKey());
                modelData.setCategory("");
                modelData.setTenantId("");

                ObjectNode modelObjectNode = objectMapper.createObjectNode();
                modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, createModelVo.getName());
                modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, createModelVo.getDescription());
                modelData.setMetaInfo(modelObjectNode.toString());
                repositoryService.saveModel(modelData);
                repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

                message.setResult(modelData);
            } catch (Throwable e) {
                message.setSuccess(false);
                message.setFailMessage("创建流程模型失败");
                message.setException(e);
            }
        }
        return message;
    }

}
