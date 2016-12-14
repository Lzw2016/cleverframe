package org.cleverframe.activiti.controller;

import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.activiti.service.TaskService;
import org.cleverframe.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/14 20:55 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/activiti/task")
public class TaskController extends BaseController {

    @Autowired
    @Qualifier(ActivitiBeanNames.TaskService)
    private TaskService taskService;


}
