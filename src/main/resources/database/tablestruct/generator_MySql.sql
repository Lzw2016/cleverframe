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
    generator_code_template -- 代码模版信息，扩展模版数据表(core_template)，一对一的关系
==================================================================================================================== */
CREATE TABLE generator_code_template
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
    name            varchar(255)    NOT NULL    UNIQUE                  COMMENT '代码模版名称，不能重复',
    node_type       char(1)         NOT NULL                            COMMENT '节点类型(0:模版分类; 1:代码模版)',
    template_ref    varchar(255)                                        COMMENT '脚本模版引用，与core_template的name字段关联',
    description     varchar(1000)   NOT NULL                            COMMENT '模版说明',
    code_type       varchar(50)     NOT NULL                            COMMENT '模版代码语言，如：java、html、jsp、sql。若是模版分类取值“Category”',
    PRIMARY KEY (id)
) COMMENT = '代码模版信息';
/*------------------------------------------------------------------------------------------------------------------------
新增：
    1.如果不是根节点，验证父节点存在，并验证父节点类型必须是[模版分类]
    2.根据节点类型设置模版引用
    3.计算出节点路径 fullPath
    4.验证代码模版名称不存在
    5.验证模版名称不存在
    6.保存模版信息Template
    7.保存代码模版CodeTemplate
    8.更新CodeTemplate fullPath属性

更新：
    1.不能修改节点类型
    2.修改节点位置，验证父节点存在，并验证父节点类型必须是[模版分类]
    3.更新CodeTemplate fullPath属性
    4.更新CodeTemplate
--------------------------------------------------------------------------------------------------------------------------*/



/*
TODO 
CodeTemplate 选择数据表 生成代码

GeneratorCodeHelper -- 帮助页面



spring-fox
*/









































































