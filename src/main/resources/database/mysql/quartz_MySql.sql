/*

In your Quartz properties file, you'll need to set
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

By: Ron Cordell - roncordell
  I didn't see this anywhere, so I thought I'd post it here. This is the script from Quartz to create the tables in a MySQL database, modified to use INNODB instead of MYISAM.

*/

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

/* ====================================================================================================================
    QRTZ_JOB_DETAILS -- 存储每一个已配置的Job的详细信息
    
    JobDetail  -- 他是实现轮询的一个的回调类，可将参数封装成JobDataMap对象,Quartz将任务的作业状态保存在JobDetail中.
    JobDataMap --  JobDataMap用来报错由JobDetail传递过来的任务实例对象
==================================================================================================================== */
CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME          VARCHAR(120)    NOT NULL    COMMENT 'Scheduler名称',
    JOB_NAME            VARCHAR(200)    NOT NULL    COMMENT 'Job key',
    JOB_GROUP           VARCHAR(200)    NOT NULL    COMMENT 'Job group 名称',
    DESCRIPTION         VARCHAR(250)    NULL        COMMENT 'Job描述， .withDescription()方法传入的string',
    JOB_CLASS_NAME      VARCHAR(250)    NOT NULL    COMMENT '实现Job的类名，trigger触发时调度此类的execute方法',
    IS_DURABLE          VARCHAR(1)      NOT NULL    COMMENT '为true时，Job相关的trigger完成以后，Job数据继续保留',
    IS_NONCONCURRENT    VARCHAR(1)      NOT NULL    COMMENT '是否不允许并发，为true时，如果下一次的触发事件到了，而上一次的job执行还未结束，则后续的触发会放入队列等待',
    IS_UPDATE_DATA      VARCHAR(1)      NOT NULL    COMMENT '是否在多次调度之间更新JobDataMap',
    REQUESTS_RECOVERY   VARCHAR(1)      NOT NULL    COMMENT 'Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发',
    JOB_DATA            BLOB            NULL        COMMENT '存储JobDataMap等',
    PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
) ENGINE=InnoDB COMMENT '存储每一个已配置的Job的详细信息';


/* ====================================================================================================================
    QRTZ_TRIGGERS -- 存储已配置的Trigger的信息
    
    SimpleTrigger <普通的Trigger> --  SimpleScheduleBuilder
    CronTrigger  <带Cron Like 表达式的Trigger> -- CronScheduleBuilder
    CalendarIntervalTrigger <带日期触发的Trigger> -- CalendarIntervalScheduleBuilder
    DailyTimeIntervalTrigger <按天触发的Trigger> -- DailyTimeIntervalScheduleBuilder
==================================================================================================================== */
CREATE TABLE QRTZ_TRIGGERS 
(
    SCHED_NAME      VARCHAR(120)        NOT NULL    COMMENT 'Scheduler名称',
    TRIGGER_NAME    VARCHAR(200)        NOT NULL    COMMENT 'Trigger key',
    TRIGGER_GROUP   VARCHAR(200)        NOT NULL    COMMENT 'Trigger group名称',
    JOB_NAME        VARCHAR(200)        NOT NULL    COMMENT 'Job key',
    JOB_GROUP       VARCHAR(200)        NOT NULL    COMMENT 'Job group 名称',
    DESCRIPTION     VARCHAR(250)        NULL        COMMENT 'Trigger 描述， .withDescription()方法传入的string',
    NEXT_FIRE_TIME  BIGINT(13)          NULL        COMMENT '下一次触发时间',
    PREV_FIRE_TIME  BIGINT(13)          NULL        COMMENT '上一次触发时间，默认-1',
    PRIORITY        INTEGER             NULL        COMMENT 'Trigger 优先级，默认5',
    TRIGGER_STATE   VARCHAR(16)         NOT NULL    COMMENT 'Trigger状态，PAUSED_BLOCKED:停止_阻塞; PAUSED:停止; WAITING:等待执行; ACQUIRED:已获得; EXECUTING:执行中; COMPLETE:完成; BLOCKED:阻塞; ERROR:错误; DELETED:已删除',
    TRIGGER_TYPE    VARCHAR(8)          NOT NULL    COMMENT 'Cron 或 Simple',
    START_TIME      BIGINT(13)          NOT NULL    COMMENT 'Trigger开始时间',
    END_TIME        BIGINT(13)          NULL        COMMENT 'Trigger结束时间',
    CALENDAR_NAME   VARCHAR(200)        NULL        COMMENT 'Trigger关联的Calendar name',
    MISFIRE_INSTR   SMALLINT(2)         NULL        COMMENT 'misfire规则id',
    JOB_DATA        BLOB                NULL        COMMENT '存储Trigger的JobDataMap等',
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
    REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
)ENGINE=InnoDB COMMENT '存储已配置的Trigger的信息';


/* ====================================================================================================================
    QRTZ_SIMPLE_TRIGGERS -- 存储简单的Trigger，包括重复次数，间隔，以及已触的次数
==================================================================================================================== */
CREATE TABLE QRTZ_SIMPLE_TRIGGERS 
(
    SCHED_NAME          VARCHAR(120)    NOT NULL    COMMENT 'Scheduler名称',
    TRIGGER_NAME        VARCHAR(200)    NOT NULL    COMMENT 'Trigger key',
    TRIGGER_GROUP       VARCHAR(200)    NOT NULL    COMMENT 'Trigger group 名称',
    REPEAT_COUNT        BIGINT(7)       NOT NULL    COMMENT '需要重复次数',
    REPEAT_INTERVAL     BIGINT(12)      NOT NULL    COMMENT '重复间隔',
    TIMES_TRIGGERED     BIGINT(10)      NOT NULL    COMMENT '已经触发次数',
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB COMMENT '存储简单的Trigger，包括重复次数、间隔、以及已触的次数';


/* ====================================================================================================================
    QRTZ_CRON_TRIGGERS -- 存储Cron Trigger，包括Cron表达式和时区信息 
==================================================================================================================== */
CREATE TABLE QRTZ_CRON_TRIGGERS 
(
    SCHED_NAME          VARCHAR(120)    NOT NULL    COMMENT 'Scheduler名称',
    TRIGGER_NAME        VARCHAR(200)    NOT NULL    COMMENT 'Trigger key',
    TRIGGER_GROUP       VARCHAR(200)    NOT NULL    COMMENT 'Trigger group 名称',
    CRON_EXPRESSION     VARCHAR(120)    NOT NULL    COMMENT '调度规则',
    TIME_ZONE_ID        VARCHAR(80)                 COMMENT '时区',
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB COMMENT '存储CronTrigger，包括Cron表达式和时区信息';
 

/* ====================================================================================================================
    QRTZ_SIMPROP_TRIGGERS -- 存储trigger属性信息
==================================================================================================================== */
CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME      VARCHAR(120)        NOT NULL    COMMENT 'Scheduler名称',
    TRIGGER_NAME    VARCHAR(200)        NOT NULL    COMMENT 'Trigger key',
    TRIGGER_GROUP   VARCHAR(200)        NOT NULL    COMMENT 'Trigger group 名称',
    STR_PROP_1      VARCHAR(512)        NULL        COMMENT '字符串属性，占位用',
    STR_PROP_2      VARCHAR(512)        NULL        COMMENT '字符串属性，占位用',
    STR_PROP_3      VARCHAR(512)        NULL        COMMENT '字符串属性，占位用',
    INT_PROP_1      INT                 NULL        COMMENT '字符串属性，占位用',
    INT_PROP_2      INT                 NULL        COMMENT '字符串属性，占位用',
    LONG_PROP_1     BIGINT              NULL        COMMENT '字符串属性，占位用',
    LONG_PROP_2     BIGINT              NULL        COMMENT '字符串属性，占位用',
    DEC_PROP_1      NUMERIC(13,4)       NULL        COMMENT '字符串属性，占位用',
    DEC_PROP_2      NUMERIC(13,4)       NULL        COMMENT '字符串属性，占位用',
    BOOL_PROP_1     VARCHAR(1)          NULL        COMMENT '字符串属性，占位用',
    BOOL_PROP_2     VARCHAR(1)          NULL        COMMENT '字符串属性，占位用',
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB COMMENT '存储trigger属性信息';


/* ====================================================================================================================
    QRTZ_BLOB_TRIGGERS -- 存储用户自定义的Trigger
==================================================================================================================== */
CREATE TABLE QRTZ_BLOB_TRIGGERS 
(
    SCHED_NAME          VARCHAR(120)    NOT NULL    COMMENT 'Scheduler名称',
    TRIGGER_NAME        VARCHAR(200)    NOT NULL    COMMENT 'Trigger key',
    TRIGGER_GROUP       VARCHAR(200)    NOT NULL    COMMENT 'Trigger group 名称',
    BLOB_DATA           BLOB            NULL        COMMENT '对于用户自定义的Trigger信息，无法提前设计字段，所以序列化后使用BLOB存储',
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB COMMENT '存储用户自定义的Trigger';


/* ====================================================================================================================
    QRTZ_CALENDARS -- 以Blob类型存储Quartz的Calendar信息
==================================================================================================================== */
CREATE TABLE QRTZ_CALENDARS 
(
    SCHED_NAME      VARCHAR(120)        NOT NULL    COMMENT 'Scheduler名称',
    CALENDAR_NAME   VARCHAR(200)        NOT NULL    COMMENT 'Calendar 名称',
    CALENDAR        BLOB                NOT NULL    COMMENT 'Calendar 数据',
    PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
)ENGINE=InnoDB COMMENT '存储Quartz的Calendar信息';


/* ====================================================================================================================
    QRTZ_PAUSED_TRIGGER_GRPS -- 存储已暂停的 Trigger 组的信息 
==================================================================================================================== */
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS 
(
    SCHED_NAME      VARCHAR(120)        NOT NULL    COMMENT 'Scheduler名称',
    TRIGGER_GROUP   VARCHAR(200)        NOT NULL    COMMENT 'Trigger group 名称',
    PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB COMMENT '存储已暂停的Trigger组的信息';


/* ====================================================================================================================
    QRTZ_FIRED_TRIGGERS -- 存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息
==================================================================================================================== */
CREATE TABLE QRTZ_FIRED_TRIGGERS 
(
    SCHED_NAME          VARCHAR(120)        NOT NULL    COMMENT 'Scheduler名称',
    ENTRY_ID            VARCHAR(95)         NOT NULL    COMMENT '条目号',
    TRIGGER_NAME        VARCHAR(200)        NOT NULL    COMMENT 'Trigger key',
    TRIGGER_GROUP       VARCHAR(200)        NOT NULL    COMMENT 'Trigger group 名称',
    INSTANCE_NAME       VARCHAR(200)        NOT NULL    COMMENT 'Scheduler实例的唯一标识（应该是完成这次调度的Scheduler标识，待多实例环境测试验证）',
    FIRED_TIME          BIGINT(13)          NOT NULL    COMMENT '触发时间',
    SCHED_TIME          BIGINT(13)          NOT NULL    COMMENT '（疑似下一次触发时间，待验证）',
    PRIORITY            INTEGER             NOT NULL    COMMENT 'Trigger 优先级',
    STATE               VARCHAR(16)         NOT NULL    COMMENT 'Trigger状态',
    JOB_NAME            VARCHAR(200)        NULL        COMMENT 'Job key',
    JOB_GROUP           VARCHAR(200)        NULL        COMMENT 'Job group 名称',
    IS_NONCONCURRENT    VARCHAR(1)          NULL        COMMENT '是否不允许并发',
    REQUESTS_RECOVERY   VARCHAR(1)          NULL        COMMENT 'Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery，如果需要会添加一个只执行一次的simple trigger重新触发',
    PRIMARY KEY (SCHED_NAME,ENTRY_ID)
)ENGINE=InnoDB COMMENT '存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息';


/* ====================================================================================================================
    QRTZ_SCHEDULER_STATE -- 存储少量的有关Scheduler的状态信息，和别的Scheduler实例(假如是用于一个集群中)
==================================================================================================================== */
CREATE TABLE QRTZ_SCHEDULER_STATE 
(
    SCHED_NAME          VARCHAR(120)    NOT NULL    COMMENT 'Scheduler名称',
    INSTANCE_NAME       VARCHAR(200)    NOT NULL    COMMENT 'Scheduler实例的唯一标识，配置文件中的Instance Id',
    LAST_CHECKIN_TIME   BIGINT(13)      NOT NULL    COMMENT '最后检入时间',
    CHECKIN_INTERVAL    BIGINT(13)      NOT NULL    COMMENT 'Scheduler 实例检入到数据库中的频率，单位毫秒',
    PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
)ENGINE=InnoDB COMMENT '存储少量的有关Scheduler的状态信息，和别的Scheduler实例';


/* ====================================================================================================================
    QRTZ_LOCKS -- 存储程序的悲观锁的信息(假如使用了悲观锁)
==================================================================================================================== */
CREATE TABLE QRTZ_LOCKS 
(
    SCHED_NAME          VARCHAR(120)    NOT NULL    COMMENT 'Scheduler名称，同一集群下的Scheduler实例名称相同，Instance_Id不同',
    LOCK_NAME           VARCHAR(40)     NOT NULL    COMMENT '锁名称，TRIGGER_ACCESS，STATE_ACCESS，JOB_ACCESS，CALENDAR_ACCESS，MISFIRE_ACCESS',
    PRIMARY KEY (SCHED_NAME,LOCK_NAME)
)ENGINE=InnoDB COMMENT '存储锁以及获得锁的调度器名称。获取的锁不存在时创建，获得锁的调度器可以操作相应数据';




CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);





/* ====================================================================================================================
    qrtz_job_log -- Job执行日志表
==================================================================================================================== */
CREATE TABLE qrtz_job_log 
(
    id                      bigint          NOT NULL    auto_increment      COMMENT '编号',
    listener_name           varchar(120)    NOT NULL                        COMMENT '监听器名称',
    sched_name              varchar(120)    NOT NULL                        COMMENT 'Scheduler名称',
    instance_name           varchar(200)    NOT NULL                        COMMENT 'Scheduler实例的唯一标识，配置文件中的Instance Id',
    job_name                varchar(200)    NOT NULL                        COMMENT 'Job key',
    job_group               varchar(200)    NOT NULL                        COMMENT 'Job group 名称',
    job_class_name          varchar(250)    NOT NULL                        COMMENT 'Job类名称',
    trigger_name            varchar(200)    NOT NULL                        COMMENT 'Trigger key',
    trigger_group           varchar(200)    NOT NULL                        COMMENT 'Trigger group名称',
    start_time              datetime        NOT NULL                        COMMENT '开始执行时间',
    end_time                datetime                                        COMMENT '执行结束时间',
    process_time            bigint                                          COMMENT '执行用时(ms)',
    pre_run_time            datetime                                        COMMENT '上一次执行时间',
    next_run_time           datetime                                        COMMENT '下一次执行时间',
    run_count               int             NOT NULL                        COMMENT '执行次数',
    ip_address              varchar(200)    NOT NULL                        COMMENT '执行节点IP,可能有多个(‘;’分隔)',
    exception_info          mediumtext                                      COMMENT '异常信息',
    is_veto                 char(1)         NOT NULL                        COMMENT '是否被否决（0：否；1：是）',
    before_job_data         mediumtext                                      COMMENT '执行前的JobDataMap数据',
    after_job_data          mediumtext                                      COMMENT '执行后的JobDataMap数据',
    PRIMARY KEY (id)
) COMMENT = 'Job执行日志表';



/* ====================================================================================================================
    qrtz_trigger_log -- Trigger触发日志表
==================================================================================================================== */
CREATE TABLE qrtz_trigger_log 
(
    id                          bigint          NOT NULL    auto_increment      COMMENT '编号',
    listener_name               varchar(120)    NOT NULL                        COMMENT '监听器名称',
    sched_name                  varchar(120)    NOT NULL                        COMMENT 'Scheduler名称',
    instance_name               varchar(200)    NOT NULL                        COMMENT 'Scheduler实例的唯一标识，配置文件中的Instance Id',
    job_name                    varchar(200)    NOT NULL                        COMMENT 'Job key',
    job_group                   varchar(200)    NOT NULL                        COMMENT 'Job group 名称',
    job_class_name              varchar(250)    NOT NULL                        COMMENT 'Job类名称',
    trigger_name                varchar(200)    NOT NULL                        COMMENT 'Trigger key',
    trigger_group               varchar(200)    NOT NULL                        COMMENT 'Trigger group名称',
    start_time                  datetime        NOT NULL                        COMMENT '开始触发时间',
    end_time                    datetime                                        COMMENT '触发完成时间',
    process_time                bigint                                          COMMENT '用时(ms)',
    pre_run_time                datetime                                        COMMENT '上一次触发时间',
    next_run_time               datetime                                        COMMENT '下一次触发时间',
    run_count                   int             NOT NULL                        COMMENT '触发次数',
    ip_address                  varchar(200)    NOT NULL                        COMMENT '触发节点IP,可能有多个(‘;’分隔)',
    mis_fired                   char(1)         NOT NULL                        COMMENT '是否错过了触发（0：否；1：是）',
    before_job_data             mediumtext                                      COMMENT '执行前的JobDataMap数据',
    after_job_data              mediumtext                                      COMMENT '执行后的JobDataMap数据',
    trigger_instruction_code    varchar(100)                                    COMMENT '触发指令码',
    instr_code                  varchar(100)                                    COMMENT '触发指令码说明',
    PRIMARY KEY (id)
) COMMENT = 'Trigger触发日志表';


/* ====================================================================================================================
    qrtz_scheduler_log -- Scheduler调度日志表
==================================================================================================================== */
CREATE TABLE qrtz_scheduler_log 
(
    id                          bigint          NOT NULL    auto_increment      COMMENT '编号',
    listener_name               varchar(120)    NOT NULL                        COMMENT '监听器名称',
    sched_name                  varchar(120)    NOT NULL                        COMMENT 'Scheduler名称',
    instance_name               varchar(200)    NOT NULL                        COMMENT 'Scheduler实例的唯一标识，配置文件中的Instance Id',
    method_name                 varchar(120)    NOT NULL                        COMMENT '触发事件调用的方法',
    log_data                    mediumtext      NOT NULL                        COMMENT '触发事件记录的日志数据',
    ip_address                  varchar(200)    NOT NULL                        COMMENT '触发节点IP,可能有多个(‘;’分隔)',
    log_time                    datetime        NOT NULL                        COMMENT '记录时间',
    PRIMARY KEY (id)
) COMMENT = 'Scheduler调度日志表';













