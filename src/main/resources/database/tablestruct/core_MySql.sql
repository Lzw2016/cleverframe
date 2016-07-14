/* -------------------------------- IdEntity --------------------------------
id              bigint          NOT NULL    auto_increment          COMMENT '编号',
company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
org_code        varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
create_date     datetime        NOT NULL                            COMMENT '创建时间',
update_by       varchar(255)    NOT NULL                            COMMENT '更新者',
update_date     datetime        NOT NULL                            COMMENT '更新时间',
remarks         varchar(255)                                        COMMENT '备注信息',
del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

PRIMARY KEY (id)

（1：正常；2：删除；3：审核）
（0：否；1：是）
（0：隐藏；1：显示）
（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）

-------------------------------- IdEntity -------------------------------- */


/* ====================================================================================================================
    core_qlscript -- 数据库脚本 SQL查询语句、HQL查询语句
==================================================================================================================== */
CREATE TABLE core_qlscript
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
    org_code        varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
    create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       varchar(255)    NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    script_type     varchar(10)     NOT NULL    DEFAULT 'SQL'           COMMENT '脚本类型，可取："SQL"、"HQL"',
    script          varchar(18000)  NOT NULL                            COMMENT '脚本，可以使用模版技术拼接',
    name            varchar(100)    NOT NULL    UNIQUE                  COMMENT '脚本名称，使用包名称+类名+方法名',
    description     varchar(1000)   NOT NULL                            COMMENT '脚本功能说明',
    PRIMARY KEY (id)
) COMMENT = '数据库脚本';
CREATE INDEX core_qlscript_name         ON  core_qlscript (name ASC);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    core_config -- 配置表
==================================================================================================================== */
CREATE TABLE core_config
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
    org_code        varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
    create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       varchar(255)    NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    config_key      varchar(100)    NOT NULL    UNIQUE                  COMMENT '配置键',
    config_value    varchar(255)    NOT NULL                            COMMENT '配置数据值',
    config_group    varchar(100)                                        COMMENT '配置组名称',
    hot_swap        char(1)         NOT NULL                            COMMENT '是否支持在线配置生效（0：否；1：是）',
    description     varchar(500)    NOT NULL                            COMMENT '描述',
    sort            int             NOT NULL    DEFAULT 0               COMMENT '排序(升序)',
    PRIMARY KEY (id)
    ) COMMENT = '配置表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    core_dict -- 字典表
==================================================================================================================== */
CREATE TABLE core_dict
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
    org_code        varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
    create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       varchar(255)    NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    dict_key        varchar(100)    NOT NULL                            COMMENT '字典键(显示值)',
    dict_value      varchar(255)    NOT NULL                            COMMENT '字典数据值(隐藏值)',
    dict_type       varchar(100)    NOT NULL                            COMMENT '字典分类',
    description     varchar(500)    NOT NULL                            COMMENT '描述',
    sort            int             NOT NULL                            COMMENT '排序(升序)',
    PRIMARY KEY (id)
) COMMENT = '字典表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    core_mdict -- 树结构字典表
==================================================================================================================== */
CREATE TABLE core_mdict
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
    org_code        varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
    create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       varchar(255)    NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    parent_id       bigint          NOT NULL                            COMMENT '父级编号,根节点的父级编号是：-1',
    full_path       varchar(255)    NOT NULL    UNIQUE                  COMMENT '树结构的全路径用“-”隔开,包含自己的ID',
    mdict_key       varchar(100)    NOT NULL                            COMMENT '字典名称',
    mdict_value     varchar(255)    NOT NULL                            COMMENT '字典数据值',
    mdict_type      varchar(100)    NOT NULL                            COMMENT '字典类型',
    description     varchar(500)    NOT NULL                            COMMENT '描述',
    sort            int             NOT NULL                            COMMENT '排序(升序)',
    PRIMARY KEY (id)
) COMMENT = '多级字典表';
/*------------------------------------------------------------------------------------------------------------------------
每一个类型的所有字典都是一颗完整的树

新增多级字典：字典类型要与父节点一致
修改多级字典：修改parent_id和mdict_type时要保证“字典类型要与父节点一致”
删除多级字典：同时删除所有子节点

修改多级字典：

if(没有修改父级编号-节点位置不变) {
    if(修改的节点不是根节点) {
        不能修改：mdict_type
    } else {
        任意修改
    }
} else(修改了父级编号-节点位置发生变化) {
    if(节点变成了根节点) {
        任意修改
    } else {
        if(修改节点的mdict_type与当前父节点不同) {
            不能修改
        } else {
            可以修改
        }
    }
}

if(修改了mdict_type) {
    更新所有子节点的mdict_type
}

if(修改了父级编号-节点位置发生变化) {
    更新所有子节点的full_path
}

重新计算full_path、mdict_type、parent_id
--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    core_template -- 模版数据表
==================================================================================================================== */
CREATE TABLE core_template
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
    org_code        varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
    create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       varchar(255)    NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    name            varchar(255)    NOT NULL    UNIQUE                  COMMENT '模版名称，不能重复',
    content         MediumText      NOT NULL                            COMMENT '模版内容',
    locale          varchar(50)     NOT NULL                            COMMENT '模版语言',
    description     varchar(1000)   NOT NULL                            COMMENT '模版说明',
    PRIMARY KEY (id)
) COMMENT = '模版数据表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


系统资源表-权限(资源)表 sys_permission
CREATE TABLE core_resources
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_id      bigint          NOT NULL                            COMMENT '数据所属公司的ID,用于公司之间的数据隔离',
    org_id          bigint          NOT NULL                            COMMENT '数据直属机构的ID',
    create_by       bigint          NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       bigint          NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    menu_id         bigint          NOT NULL                            COMMENT '所属菜单,系统模块菜单ID',
    permission      varchar(100)    NOT NULL    UNIQUE                  COMMENT '权限标识字符串',
    permission_type char(1)         NOT NULL                            COMMENT '权限类型（1：URL权限；2：UI权限）',
    name            varchar(50)     NOT NULL                            COMMENT '权限名称',
    url             varchar(255)    NOT NULL                            COMMENT '权限对应的请求地址(冗余数据,只是用来查看的,要以代码中的权限注解为准)',
    PRIMARY KEY (id)
) COMMENT = '权限(资源)表';







/* ====================================================================================================================
    core_access_log -- 系统访问日志表
==================================================================================================================== */
CREATE TABLE core_access_log
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    login_name      varchar(20)                                         COMMENT '登录名',
    request_time    datetime        NOT NULL                            COMMENT '请求时间',
    request_uri     varchar(255)    NOT NULL                            COMMENT '请求URI',
    method          varchar(20)     NOT NULL                            COMMENT '操作方式',
    params          MediumText                                          COMMENT '操作提交的数据',
    process_time    bigint          NOT NULL                            COMMENT '请求处理时间',
    remote_addr     varchar(30)                                         COMMENT '客户端的IP地址',
    user_agent      varchar(100)                                        COMMENT '用户代理',
    has_exception   char(1)         NOT NULL    DEFAULT '1'             COMMENT '是否有异常（0：否；1：是）',
    exception_info  MediumText                                          COMMENT '异常信息',
    PRIMARY KEY (id)
) COMMENT = '日志表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/




























