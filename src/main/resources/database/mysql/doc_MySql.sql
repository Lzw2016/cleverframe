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
    doc_project -- 文档项目表
==================================================================================================================== */
CREATE TABLE doc_project
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

    name            varchar(100)    NOT NULL    UNIQUE                  COMMENT '文档项目名称',
    readme          MediumText      NOT NULL                            COMMENT '文档介绍和说明',
    summary         MediumText      NOT NULL                            COMMENT '文档目录',
    PRIMARY KEY (id)
) COMMENT = '文档项目表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    doc_document -- 文档表
==================================================================================================================== */
CREATE TABLE doc_document
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

    project_name    varchar(100)    NOT NULL                            COMMENT '文档项目名称-关联doc_project(文档项目表)',
    parent_id       bigint          NOT NULL                            COMMENT '父级编号,根节点的父级编号是：-1',
    full_path       varchar(255)    NOT NULL    UNIQUE                  COMMENT '树结构的全路径用“-”隔开,包含自己的ID',
    title           varchar(100)    NOT NULL                            COMMENT '文档或者章节的标题',
    content         MediumText      NOT NULL                            COMMENT '文档内容',
    is_directory    char(1)         NOT NULL                            COMMENT '是否是目录（0：否；1：是）',
    PRIMARY KEY (id)
) COMMENT = '文档表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    doc_history -- 文档历史表
==================================================================================================================== */
CREATE TABLE doc_history
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    create_by       varchar(255)    NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    document_id     bigint          NOT NULL                            COMMENT '文档ID-关联doc_document(文档表)',
    content         MediumText      NOT NULL                            COMMENT '文档内容',
    PRIMARY KEY (id)
) COMMENT = '文档历史表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/







