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
    sys_role_menu -- 角色-菜单
==================================================================================================================== */
CREATE TABLE sys_role_menu
(
    role_id         bigint          NOT NULL                            COMMENT '角色编号',
    menu_id         bigint          NOT NULL                            COMMENT '菜单编号',
    PRIMARY KEY (role_id, menu_id)
) COMMENT = '角色-菜单';
/*------------------------------------------------------------------------------------------------------------------------
角色增加菜单：
1.角色存在，且状态可用?
2.菜单存在，且状态可用?
3.菜单存在父菜单，角色是否拥有其父菜单？ -- 不需要

角色删除菜单：
1.角色存在，且状态可用?
2.菜单存在，且状态可用?
3.菜单存在子菜单，是否删除其所有子菜单？ -- 不需要
4.是否删除角色拥有该菜单下的UI权限？ -- 暂不需要
--------------------------------------------------------------------------------------------------------------------------*/











/* =================================================================================================================================================== */
/* =================================================================================================================================================== */
/* =================================================================================================================================================== */
/* =================================================================================================================================================== */

/* ====================================================================================================================
    sys_resources -- 系统资源(权限)表
==================================================================================================================== */
CREATE TABLE sys_resources
(
    id                  bigint          NOT NULL    auto_increment          COMMENT '编号',
    title               varchar(255)    NOT NULL                            COMMENT '资源标题',
    resources_url       varchar(255)    NOT NULL    UNIQUE                  COMMENT '资源URL地址',
    permission          varchar(255)    NOT NULL    UNIQUE                  COMMENT '资源访问所需要的权限标识字符串',
    resources_type      char(1)         NOT NULL                            COMMENT '资源类型（1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源）',
    need_authorization  char(1)         NOT NULL                            COMMENT '需要授权才允许访问（1：需要；2：不需要）',
    description         MediumText      NOT NULL                            COMMENT '资源说明',
    PRIMARY KEY (id)
) COMMENT = '系统资源(权限)表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_role -- 角色表
==================================================================================================================== */
CREATE TABLE sys_role
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

    name            varchar(50)     NOT NULL    UNIQUE                  COMMENT '角色名称',
    description     varchar(2000)   NOT NULL                            COMMENT '角色说明',
    PRIMARY KEY (id)
) COMMENT = '角色表';
CREATE INDEX sys_role_name ON sys_role (name ASC);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_role_resources -- 角色资源
==================================================================================================================== */
CREATE TABLE sys_role_resources
(
    role_id         bigint          NOT NULL                            COMMENT '角色编号',
    resources_id    bigint          NOT NULL                            COMMENT '资源ID,关联表:sys_resources',
    PRIMARY KEY (role_id, resources_id)
) COMMENT = '角色资源';
/*------------------------------------------------------------------------------------------------------------------------
角色增加权限：
1.角色存在，且状态可用?
2.权限角色存在，且状态可用?
3.若是UI权限，该角色是否拥有其菜单？ -- 暂不需要

角色删除权限：
1.角色存在，且状态可用?
2.权限角色存在，且状态可用?
--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_user -- 用户表
==================================================================================================================== */
CREATE TABLE sys_user
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

    home_company    varchar(255)    NOT NULL                            COMMENT '归属公司',
    home_org        varchar(255)    NOT NULL                            COMMENT '直属机构',
    login_name      varchar(100)    NOT NULL    UNIQUE                  COMMENT '登录名，不能修改',
    password        varchar(100)    NOT NULL                            COMMENT '密码',
    job_no          varchar(30)     NOT NULL    UNIQUE                  COMMENT '工号',
    name            varchar(100)    NOT NULL                            COMMENT '姓名',
    sex             char(1)         NOT NULL                            COMMENT '性别（1：男；2：女）',
    birthday        datetime        NOT NULL                            COMMENT '生日',
    email           varchar(100)                                        COMMENT '邮箱',
    phone           varchar(100)                                        COMMENT '电话',
    mobile          varchar(100)                                        COMMENT '手机',
    user_type       char(1)         NOT NULL                            COMMENT '用户类型（1：内部用户；2：外部用户）',
    login_ip        varchar(100)                                        COMMENT '最后登陆IP',
    login_date      datetime                                            COMMENT '最后登陆时间',
    account_state   char(1)         NOT NULL    DEFAULT '1'             COMMENT '用户帐号状态(1：正常；2：锁定；3：删除)',
    user_state      char(1)         NOT NULL                            COMMENT '用户状态(1：试用；2：在职；3：离职)',
    PRIMARY KEY (id)
) COMMENT = '用户表';
CREATE INDEX sys_user_login_name    ON sys_user (login_name ASC);
/*------------------------------------------------------------------------------------------------------------------------
帐号状态：正常、锁定、删除
用户--是机构树的叶子节点
常用群组功能***

新建用户：验证用户“归属公司”和“直属机构”关系
修改用户：用户更新不能修改的字段：login_name、job_no、login_ip、login_date、password(password需要独立修改？？)
删除用户：用户不能删除，只能禁用（account_state = 3）
--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_user_role -- 用户-角色
==================================================================================================================== */
CREATE TABLE sys_user_role
(
    user_id         bigint          NOT NULL                            COMMENT '用户编号',
    role_id         bigint          NOT NULL                            COMMENT '角色编号',
    PRIMARY KEY (user_id, role_id)
) COMMENT = '用户-角色';
/*------------------------------------------------------------------------------------------------------------------------
用户增加角色：
1.用户存在，且状态可用？
2.角色存在，且状态可用？
3.用户与角色属于同一个公司？

用户删除角色：
1.用户存在，且状态可用？
2.角色存在，且状态可用？
3.用户与角色属于同一个公司？
--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_resources_relation -- 系统资源关系表
==================================================================================================================== */
CREATE TABLE sys_resources_relation
(
    resources_id                bigint          NOT NULL                            COMMENT '资源ID',
    dependence_resources_id     bigint          NOT NULL                            COMMENT '依赖的资源ID,如：数据请求URL地址、Web页面UI',
    PRIMARY KEY (resources_id, dependence_resources_id)
) COMMENT = '系统资源关系表';
/*------------------------------------------------------------------------------------------------------------------------ 
JSP页面
    UI资源
    数据请求URL地址

JSP页面表(菜单)

JSP页面内资源

JSP页面-JSP页面内资源 关系表

系统资源(权限) 管理， 增删查改
系统资源关系表 管理，增删查改
--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_menu -- 菜单表
==================================================================================================================== */
CREATE TABLE sys_menu
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
    full_path       varchar(255)    NOT NULL                            COMMENT '树结构的全路径用“-”隔开,包含自己的ID',
    menu_type       varchar(255)    NOT NULL    DEFAULT '1'             COMMENT '菜单类型',
    name            varchar(50)     NOT NULL                            COMMENT '菜单名称',
    resources_id    bigint                                              COMMENT '资源ID,关联表:sys_resources',
    icon            varchar(50)                                         COMMENT '图标',
    open_mode       char(1)         NOT NULL    DEFAULT '1'             COMMENT '菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)',
    sort            int             NOT NULL                            COMMENT '排序(升序)',
    PRIMARY KEY (id)
) COMMENT = '菜单表';
/*------------------------------------------------------------------------------------------------------------------------
菜单类别：所有同一类别的菜单应该是一颗完整的菜单树
菜单类型：所有菜单菜单类别是系统模块名的菜单都是系统模块菜单,菜单类别是根据login_name来命名的都是个人快捷菜单
同一棵树上的菜单，菜单类别、菜单类型都应该一致

新建菜单：菜单验证（上级菜单、菜单类别、菜单类型）
修改菜单：不能修改（full_path、category、menu_type），修改parent_id需要自动更新full_path、category、menu_type（包括子节点）
删除菜单：删除一个菜单必须删除所有子菜单


修改菜单：

if(没有修改父级编号-节点位置不变)
{
    if(修改的节点不是根节点)
    {
        不能修改：category、menu_type
    }
    else
    {
        任意修改
    }
}
else(修改了父级编号-节点位置发生变化)
{
    if(节点变成了根节点)
    {
        任意修改
    }
    else
    {
        if(修改节点的category、menu_type与当前父节点不同)
        {
            不能修改
        }
        else
        {
            可以修改
        }
    }
}

if(修改了category、menu_type)
{
    更新所有子节点的category、menu_type
}

if(修改了父级编号-节点位置发生变化)
{
    更新所有子节点的full_path
}

重新计算full_path、category、menu_type、parent_id
--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_organization -- 组织机构表
==================================================================================================================== */
CREATE TABLE sys_organization
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

    parent_id       bigint          NOT NULL                            COMMENT '父级机构ID,根节点的父级机构ID是：-1',
    full_path       varchar(255)    NOT NULL                            COMMENT '树结构的全路径用“-”隔开,包含自己的ID',
    code            varchar(255)    NOT NULL    UNIQUE                  COMMENT '机构编码，如：001002...',
    name            varchar(100)    NOT NULL                            COMMENT '机构名称',
    org_type        char(1)         NOT NULL                            COMMENT '机构类型,从数据字典中获取（1: 集团；2：区域；3：公司；4：部门；5：小组）',
    address         varchar(255)                                        COMMENT '机构地址',
    zip_code        varchar(50)                                         COMMENT '邮政编码',
    master          varchar(100)                                        COMMENT '负责人',
    phone           varchar(100)                                        COMMENT '电话',
    fax             varchar(100)                                        COMMENT '传真',
    email           varchar(100)                                        COMMENT '邮箱',
    PRIMARY KEY (id)
) COMMENT = '组织机构表';
CREATE INDEX sys_organization_full_path     ON  sys_organization     (full_path ASC); -- 会根据节点全路径like匹配当前节点所有子节点等操作
/*------------------------------------------------------------------------------------------------------------------------
机构类型：集团、片区/大区、分公司/公司、部门/中心--从数据字典表中获取
机构路径：XXX-XXX-XXX-XXX-XXX  -ID

机构新建：机构新建规则，顶级机构、当前机构的下级机构、机构类型从数据字典中获取
机构修改：不能修改：parent_id、full_path、code、org_type
机构删除：机构不能删除，机构只能停用(软删除)，而且只能停用一个空机构
--------------------------------------------------------------------------------------------------------------------------*/

/* ====================================================================================================================
    sys_login_session -- 用户登录日志表
==================================================================================================================== */
CREATE TABLE sys_login_session
(
    id                  bigint          NOT NULL    auto_increment          COMMENT '编号',
    create_date         datetime        NOT NULL                            COMMENT '创建时间',
    update_date         datetime                                            COMMENT '更新时间',
    session_id          varchar(64)     NOT NULL    UNIQUE                  COMMENT '资源标题',
    login_name          varchar(100)    NOT NULL                            COMMENT '用户登录名',                                            
    session_object      MediumBlob      NOT NULL                            COMMENT 'Shiro框架Session对象序列化数据',
    PRIMARY KEY (id)
) COMMENT = '存储Shiro框架Session数据(此表数据只是用户登入Session数据，必要时可清空此表)';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    sys_login_log -- 用户登录日志表
==================================================================================================================== */
CREATE TABLE sys_login_log
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    login_name      varchar(100)    NOT NULL                            COMMENT '登录名',
    login_time      datetime        NOT NULL                            COMMENT '登录时间',
    login_ip        varchar(30)                                         COMMENT '登录的IP地址',
    user_agent      varchar(100)                                        COMMENT '用户代理，客户端信息或浏览器信息',
    user_info       varchar(5000)                                       COMMENT '用户信息，Json格式数据',
    PRIMARY KEY (id)
) COMMENT = '用户登录日志表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/






