/* -------------------------------- IdEntity --------------------------------
id              bigint          NOT NULL    auto_increment          COMMENT '编号',
company_code    varchar(255)    NOT NULL                            COMMENT '数据所属公司的机构编码',
office_code     varchar(255)    NOT NULL                            COMMENT '数据直属机构的编码',
create_by       bigint          NOT NULL                            COMMENT '创建者',
create_date     datetime        NOT NULL                            COMMENT '创建时间',
update_by       bigint          NOT NULL                            COMMENT '更新者',
update_date     datetime        NOT NULL                            COMMENT '更新时间',
remarks         varchar(255)                                        COMMENT '备注信息',
del_flag        char(1)         NOT NULL    DEFAULT '0'             COMMENT '删除标记（1：正常；2：删除；3：审核）',
uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

PRIMARY KEY (id)

（1：正常；2：删除；3：审核）
（0：否；1：是）
（0：隐藏；1：显示）
（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）

-------------------------------- IdEntity -------------------------------- */

/* ====================================================================================================================
    fileupload -- 文件上传
==================================================================================================================== */

CREATE TABLE fileupload_fileinfo
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    company_code    bigint          NOT NULL                            COMMENT '数据所属公司的ID,用于公司之间的数据隔离',
    org_id          bigint          NOT NULL                            COMMENT '数据直属机构的ID',
    create_by       bigint          NOT NULL                            COMMENT '创建者',
    create_date     datetime        NOT NULL                            COMMENT '创建时间',
    update_by       bigint          NOT NULL                            COMMENT '更新者',
    update_date     datetime        NOT NULL                            COMMENT '更新时间',
    remarks         varchar(255)                                        COMMENT '备注信息',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT '删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准',
    uuid            varchar(36)     NOT NULL                            COMMENT '数据全局标识UUID',

    stored_type     char(1)         NOT NULL                            COMMENT '上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）',
    file_path       varchar(255)    NOT NULL                            COMMENT '上传文件存放路径',
    digest          varchar(255)    NOT NULL                            COMMENT '文件签名，用于判断是否是同一文件',
    digest_type     char(1)         NOT NULL                            COMMENT '文件签名算法类型（1：MD5；2：SHA-1；）',
    file_size       bigint          NOT NULL                            COMMENT '文件大小，单位：byte(1KB = 1024byte)',
    file_name       varchar(255)    NOT NULL                            COMMENT '文件原名称，用户上传时的名称',
    new_name        varchar(255)    NOT NULL                            COMMENT '文件当前名称（UUID + 后缀名）',
    upload_time     bigint          NOT NULL                            COMMENT '文件上传所用时间',
    stored_time     bigint          NOT NULL                            COMMENT '文件存储用时，单位：毫秒（此时间只包含服务器端存储文件所用的时间，不包括文件上传所用的时间）',
    file_source     varchar(255)    NOT NULL                            COMMENT '文件来源（可以是系统模块名）',
    PRIMARY KEY (id)
) COMMENT = '上传文件信息表';
CREATE INDEX fileupload_fileinfo_digest     ON  fileupload_fileinfo     (digest ASC);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    ftp_user -- ftp服务器用户
==================================================================================================================== */

CREATE TABLE ftp_user (
    userid          varchar(64)     NOT NULL                            COMMENT '用户ID',
    userpassword    varchar(64)                                         COMMENT '用户密码',
    homedirectory   varchar(128)    NOT NULL                            COMMENT '主目录',
    enableflag      boolean                     DEFAULT true            COMMENT '当前用户可用',
    writepermission boolean                     DEFAULT false           COMMENT '具有上传权限',
    idletime        int                         DEFAULT 0               COMMENT '空闲时间',
    uploadrate      int                         DEFAULT 0               COMMENT '上传速率限制（字节每秒）',
    downloadrate    int                         DEFAULT 0               COMMENT '下载速率限制（字节每秒）',
    maxloginnumber  int                         DEFAULT 0               COMMENT '最大登陆用户数',
    maxloginperip   int                         DEFAULT 0               COMMENT '同IP登陆用户数',
    PRIMARY KEY (userid)
) COMMENT = 'FTP服务器用户';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


CREATE TABLE sys_template_file
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

  fileinfo_id     bigint          NOT NULL                            COMMENT '上传文件信息表ID',
  name            varchar(100)    NOT NULL    UNIQUE                  COMMENT '模版名称',
  PRIMARY KEY (id)
) COMMENT = '模版文件表';
/*------------------------------------------------------------------------------------------------------------------------

增加模板文件：验证对应的上传文件(fileinfo_id)存不存在，模版名称不能重复
修改模板文件：验证对应的上传文件(fileinfo_id)存不存在，模版名称不能重复
删除模板文件：软删除，一同删除关联的"上传文件信息表"数据

--------------------------------------------------------------------------------------------------------------------------*/















/*
用户云盘文件

文件引用ID

文件名称
文件创建人
文件创建时间

文件路径
*/
































































