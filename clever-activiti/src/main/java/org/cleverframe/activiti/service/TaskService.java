package org.cleverframe.activiti.service;

import org.cleverframe.activiti.ActivitiBeanNames;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/14 20:53 <br/>
 */
@Service(ActivitiBeanNames.TaskService)
public class TaskService  extends BaseService {

    @Autowired
    @Qualifier(SpringBeanNames.TaskService)
    private org.activiti.engine.TaskService taskService;
}
