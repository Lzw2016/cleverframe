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



-- 用户登录记







































/*
SYS模块管理页面
    数据库脚本管理
    字典管理
    机构管理
    用户管理
    角色管理
    菜单管理
    权限管理
    多级字典管理
    用户角色权限管理
    ***
    用户角色管理
    角色菜单管理
    角色权限管理
    用户所拥有的“角色、菜单、权限”查看
    角色拥有的“菜单、权限”查看
    ***
    Shiro权限管理(缓存)

sys_role 角色

sys_menu 菜单

sys_permission 权限

sys_user_role  一个用户有多个角色

sys_role_permission 一个角色可以有多个权限

sys_role_menu 一个角色可以使用访问多个菜单？意义：

sys_menu_permission 一个菜单所含有的资源权限，只读！！开发时维护

sys_organization_role (sys_role_office) 一个机构可以有多个角色？意义：无意义，只要数据按公司隔离

1.DAO、Service类查询数据库方法参数必须是查询SQL的参数值。
2.DAO、Service类可以通过抛出异常或使用AjaxMessage参数向Controller返回操作信息。
3.默认不区分公司的数据，fileupload_fileinfo、qlscript_qlscript、ftp_user、sys_menu、sys_permission、sys_template_file
*/

CREATE TABLE sys_organization
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

    parent_id       bigint          NOT NULL                            COMMENT '父级编号,根节点的父级编号是：-1',
    full_path       varchar(255)    NOT NULL                            COMMENT '树结构的全路径用“-”隔开,包含自己的ID',
    code            varchar(255)    NOT NULL    UNIQUE                  COMMENT '机构编码，如：001002...',
    name            varchar(100)    NOT NULL                            COMMENT '机构名称',
    org_type        char(1)         NOT NULL                            COMMENT '机构类型（1: 集团；2：区域；3：公司；4：部门；5：小组）',
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


CREATE TABLE sys_user
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

    home_company    bigint          NOT NULL                            COMMENT '归属公司',
    home_org        bigint          NOT NULL                            COMMENT '直属机构',
    login_name      varchar(20)     NOT NULL    UNIQUE                  COMMENT '登录名，不能修改',
    password        varchar(100)    NOT NULL                            COMMENT '密码',
    job_no          varchar(30)     NOT NULL    UNIQUE                  COMMENT '工号',
    name            varchar(30)     NOT NULL                            COMMENT '姓名',
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


CREATE TABLE sys_role
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

    name            varchar(50)     NOT NULL    UNIQUE                  COMMENT '角色名称',
    PRIMARY KEY (id)
) COMMENT = '角色表';
CREATE INDEX sys_role_name ON sys_role (name ASC);
/*------------------------------------------------------------------------------------------------------------------------


--------------------------------------------------------------------------------------------------------------------------*/


CREATE TABLE sys_menu
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

    parent_id       bigint          NOT NULL                            COMMENT '父级编号,根节点的父级编号是：-1',
    full_path       varchar(255)    NOT NULL                            COMMENT '树结构的全路径用“-”隔开,包含自己的ID',
    category        varchar(100)    NOT NULL                            COMMENT '菜单类别,如：系统不同模块的菜单(模块名)、个人快捷菜单(login_name)',-- 模块名称
    menu_type       char(1)         NOT NULL    DEFAULT '1'             COMMENT '菜单类型（1：系统模块菜单，2：个人快捷菜单）',-- "System" 表示系统菜单，"login_name" 表示个人快捷菜单
    name            varchar(50)     NOT NULL                            COMMENT '菜单名称',
    href            varchar(255)                                        COMMENT '菜单地址',
    icon            varchar(50)                                         COMMENT '图标',
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


CREATE TABLE sys_permission
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
/*------------------------------------------------------------------------------------------------------------------------

用户 —— 角色 —— 权限
用户 —— 角色 —— 菜单 —— 权限
url：url后台权限 和 前台按钮权限

sys_permission是开发人员使用的表，用户是不能操作的

新增权限：验证菜单是否存在，URL权限menu_id=-1；UI权限menu_id必须存在
修改权限：验证菜单是否存在，URL权限menu_id=-1；UI权限menu_id必须存在
删除权限：软删除，一些特殊权限不能删除，如：管理员权限

--------------------------------------------------------------------------------------------------------------------------*/


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


CREATE TABLE sys_role_permission
(
    role_id         bigint          NOT NULL                            COMMENT '角色编号',
    permission_id   bigint          NOT NULL                            COMMENT '权限编号',
    PRIMARY KEY (role_id, permission_id)
) COMMENT = '角色-权限';
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
    sys_login_log -- 用户登入日志表
==================================================================================================================== */
CREATE TABLE sys_login_log
(
    id              bigint          NOT NULL    auto_increment          COMMENT '编号',
    login_name      varchar(20)     NOT NULL                            COMMENT '登录名',
    login_time      datetime        NOT NULL                            COMMENT '登录时间',
    login_ip        varchar(30)                                         COMMENT '登录的IP地址',
    user_agent      varchar(100)                                        COMMENT '用户代理，客户端信息或浏览器信息',
    user_info       varchar(1000)                                       COMMENT '用户信息，Json格式数据',
    PRIMARY KEY (id)
) COMMENT = '用户登入日志表';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/



