package org.cleverframe.activiti;

import org.cleverframe.common.IJspUrlPath;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/1 18:30 <br/>
 */
public class ActivitiJspUrlPath implements IJspUrlPath {
    /**
     * 流程部署信息
     */
    public static final String Deployment = "activiti/Deployment";
    /**
     * 流程模型管理
     */
    public static final String Model = "activiti/Model";
    /**
     * 流程定义
     */
    public static final String ProcessDefinition = "activiti/ProcessDefinition";
    /**
     * 流程实例
     */
    public static final String ProcessInstance = "activiti/ProcessInstance";
}
