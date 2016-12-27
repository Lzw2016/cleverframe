/*

账号	密码	角色
kermit	kermit	admin
gonzo	gonzo	manager
fozzie	fozzie	user



1   act_ge_bytearray 二进制数据表
2   act_ge_property 属性数据表存储整个流程引擎级别的数据,初始化表结构时，会默认插入三条记录，
    
3   act_hi_actinst 历史节点表
4   act_hi_attachment 历史附件表
5   act_hi_comment 历史意见表
6   act_hi_identitylink 历史流程人员表
7   act_hi_detail 历史详情表，提供历史变量的查询
8   act_hi_procinst 历史流程实例表
9   act_hi_taskinst 历史任务实例表
10  act_hi_varinst 历史变量表
    
11  act_id_group 用户组信息表
12  act_id_info 用户扩展信息表
13  act_id_membership 用户与用户组对应信息表
14  act_id_user 用户信息表
    
15  act_re_deployment 部署信息表
16  act_re_model 流程设计模型部署表
17  act_re_procdef 流程定义数据表
    
18  act_ru_event_subscr throwEvent、catchEvent时间监听信息表
19  act_ru_execution 运行时流程执行实例表
20  act_ru_identitylink 运行时流程人员表，主要存储任务节点与参与者的相关信息
21  act_ru_job 运行时定时任务数据表
22  act_ru_task 运行时任务节点表
23  act_ru_variable 运行时流程变量数据表




/form/form-data
/history/historic-activity-instances
/history/historic-detail
/history/historic-detail/{detailId}/data
/history/historic-process-instances
/history/historic-process-instances/{processInstanceId}
/history/historic-process-instances/{processInstanceId}/comments
/history/historic-process-instances/{processInstanceId}/comments/{commentId}
/history/historic-process-instances/{processInstanceId}/identitylinks
/history/historic-process-instances/{processInstanceId}/variables/{variableName}/data
/history/historic-task-instances
/history/historic-task-instances/{taskId}
/history/historic-task-instances/{taskId}/identitylinks
/history/historic-task-instances/{taskId}/variables/{variableName}/data
/history/historic-variable-instances
/history/historic-variable-instances/{varInstanceId}/data
/identity/groups
/identity/groups/{groupId}
/identity/groups/{groupId}/members
/identity/groups/{groupId}/members/{userId}
/identity/users
/identity/users/{userId}
/identity/users/{userId}/info
/identity/users/{userId}/info/{key}
/identity/users/{userId}/picture
/management/engine
/management/jobs
/management/jobs/{jobId}
/management/jobs/{jobId}/exception-stacktrace
/management/properties
/management/tables
/management/tables/{tableName}
/management/tables/{tableName}/columns
/management/tables/{tableName}/data
/process-definition/{processDefinitionId}/properties
/query/executions
/query/historic-activity-instances
/query/historic-detail
/query/historic-process-instances
/query/historic-task-instances
/query/historic-variable-instances
/query/process-instances
/query/tasks
/repository/deployments
/repository/deployments/{deploymentId}
/repository/deployments/{deploymentId}/resourcedata/{resourceId}
/repository/deployments/{deploymentId}/resources
/repository/deployments/{deploymentId}/resources/**
/repository/models
/repository/models/{modelId}
/repository/models/{modelId}/source
/repository/models/{modelId}/source-extra
/repository/process-definitions
/repository/process-definitions/{processDefinitionId}
/repository/process-definitions/{processDefinitionId}/identitylinks
/repository/process-definitions/{processDefinitionId}/identitylinks/{family}/{identityId}
/repository/process-definitions/{processDefinitionId}/image
/repository/process-definitions/{processDefinitionId}/model
/repository/process-definitions/{processDefinitionId}/resourcedata
/runtime/executions
/runtime/executions/{executionId}
/runtime/executions/{executionId}/activities
/runtime/executions/{executionId}/variables
/runtime/executions/{executionId}/variables/{variableName}
/runtime/executions/{executionId}/variables/{variableName}/data
/runtime/process-instances
/runtime/process-instances/{processInstanceId}
/runtime/process-instances/{processInstanceId}/diagram
/runtime/process-instances/{processInstanceId}/identitylinks
/runtime/process-instances/{processInstanceId}/identitylinks/users/{identityId}/{type}
/runtime/process-instances/{processInstanceId}/variables
/runtime/process-instances/{processInstanceId}/variables/{variableName}
/runtime/process-instances/{processInstanceId}/variables/{variableName}/data
/runtime/signals
/runtime/tasks
/runtime/tasks/{taskId}
/runtime/tasks/{taskId}/attachments
/runtime/tasks/{taskId}/attachments/{attachmentId}
/runtime/tasks/{taskId}/attachments/{attachmentId}/content
/runtime/tasks/{taskId}/comments
/runtime/tasks/{taskId}/comments/{commentId}
/runtime/tasks/{taskId}/events
/runtime/tasks/{taskId}/events/{eventId}
/runtime/tasks/{taskId}/identitylinks
/runtime/tasks/{taskId}/identitylinks/{family}
/runtime/tasks/{taskId}/identitylinks/{family}/{identityId}/{type}
/runtime/tasks/{taskId}/subtasks
/runtime/tasks/{taskId}/variables
/runtime/tasks/{taskId}/variables/{variableName}
/runtime/tasks/{taskId}/variables/{variableName}/data
/simple-workflow








assignee 流程处理人 OWNER  https://my.oschina.net/acitiviti/blog/350957
candidateUsers 流程处理候选人
candidateGroups 路程处理候选组


activiti 多实例任务
activiti:candidateUsers="shareniu1,shareniu2,shareniu3,shareniu4" 这个节点可以4个人审核。
<loopCardinality>2</loopCardinality> 循环2次结束。
<multiInstanceLoopCharacteristics isSequential="true"> 串行并行的配置。
<completionCondition>${nrOfCompletedInstances/nrOfInstances >= 0.25}</completionCondition> 完成条件的配置。
如果使用串行方式操作nrOfActiveInstances 变量始终是1，因为并行的时候才会去+1操作。


流程通过、驳回、会签、转办、中止、挂起
http://blog.csdn.net/rosten/article/details/38300267


TaskActionRequest
action  complete claim delegate resolve
assignee
variables[]
    name
    type
    variableScope LOCAL, GLOBAL
    value
    valueUrl

{
	"action": "complete",
	"variables": [{
			"name": "k3",
			"type": "string",
			"value": "v3",
			"scope": "global"
		}, {
			"name": "k4",
			"type": "string",
			"value": "v4",
			"scope": "global"
		}
	]
}

查询所有的工作流定义 (查看流程图、编辑流程、删除流程、启动流程)

查询用户待办任务 (查看流程实例图、审核流程、查看流程审核历史、查看流程数据)

查询历史审核流程 (查看流程实例图、查看流程审核历史、查看流程数据)

*/





