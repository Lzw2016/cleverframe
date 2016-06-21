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
    template_ref    varchar(255)    NOT NULL                            COMMENT '脚本模版引用，与core_template的name字段关联',
    description     varchar(1000)   NOT NULL                            COMMENT '模版说明',
    code_type       varchar(50)     NOT NULL                            COMMENT '模版代码语言，如：java、html、jsp、sql',
    PRIMARY KEY (id)
) COMMENT = '代码模版信息';










































