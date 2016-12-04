package org.cleverframe.activiti.controller;

import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.service.RepositoryService;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
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
}
