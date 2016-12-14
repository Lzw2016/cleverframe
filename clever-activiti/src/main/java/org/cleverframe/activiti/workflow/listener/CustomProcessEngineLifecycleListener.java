package org.cleverframe.activiti.workflow.listener;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/13 14:29 <br/>
 */
public class CustomProcessEngineLifecycleListener implements ProcessEngineLifecycleListener {
    private static Logger logger = LoggerFactory.getLogger(CustomProcessEngineLifecycleListener.class);

    /*
    * 流程引擎创建时事件
    * */
    @Override
    public void onProcessEngineBuilt(ProcessEngine processEngine) {
        logger.info("### Activiti流程引擎创建");
    }

    /*
    * 流程引擎关闭时事件
    * */
    @Override
    public void onProcessEngineClosed(ProcessEngine processEngine) {
        logger.info("### Activiti流程引擎关闭");
    }
}
