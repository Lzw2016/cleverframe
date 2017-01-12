/* -------------------------------- IdEntity --------------------------------
id              bigint          NOT NULL    auto_increment          COMMENT '���',
company_code    varchar(255)    NOT NULL                            COMMENT '����������˾�Ļ�������',
org_code        varchar(255)    NOT NULL                            COMMENT '����ֱ�������ı���',
create_by       varchar(255)    NOT NULL                            COMMENT '������',
create_date     datetime        NOT NULL                            COMMENT '����ʱ��',
update_by       varchar(255)    NOT NULL                            COMMENT '������',
update_date     datetime        NOT NULL                            COMMENT '����ʱ��',
remarks         varchar(255)                                        COMMENT '��ע��Ϣ',
del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT 'ɾ����ǣ�1��������2��ɾ����3����ˣ�',
uuid            varchar(36)     NOT NULL                            COMMENT '����ȫ�ֱ�ʶUUID',

PRIMARY KEY (id)

��1��������2��ɾ����3����ˣ�
��0����1���ǣ�
��0�����أ�1����ʾ��
��1���������ݣ�2�����ڹ�˾���������ݣ�3�����ڹ�˾���ݣ�4�����ڻ������������ݣ�5�����ڻ������ݣ�8�����������ݣ�9������ϸ���ã�

-------------------------------- IdEntity -------------------------------- */


/* ====================================================================================================================
    doc_project -- �ĵ���Ŀ��
==================================================================================================================== */
CREATE TABLE doc_project
(
    id              bigint          NOT NULL    auto_increment          COMMENT '���',
    company_code    varchar(255)    NOT NULL                            COMMENT '����������˾�Ļ�������',
    org_code        varchar(255)    NOT NULL                            COMMENT '����ֱ�������ı���',
    create_by       varchar(255)    NOT NULL                            COMMENT '������',
    create_date     datetime        NOT NULL                            COMMENT '����ʱ��',
    update_by       varchar(255)    NOT NULL                            COMMENT '������',
    update_date     datetime        NOT NULL                            COMMENT '����ʱ��',
    remarks         varchar(255)                                        COMMENT '��ע��Ϣ',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT 'ɾ����ǣ�1��������2��ɾ����3����ˣ�',
    uuid            varchar(36)     NOT NULL                            COMMENT '����ȫ�ֱ�ʶUUID',

    name            varchar(100)    NOT NULL    UNIQUE                  COMMENT '�ĵ���Ŀ����',
    readme          MediumText      NOT NULL                            COMMENT '�ĵ����ܺ�˵��',
    summary         MediumText      NOT NULL                            COMMENT '�ĵ�Ŀ¼',
    PRIMARY KEY (id)
) COMMENT = '�ĵ���Ŀ��';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    doc_document -- �ĵ���
==================================================================================================================== */
CREATE TABLE doc_document
(
    id              bigint          NOT NULL    auto_increment          COMMENT '���',
    company_code    varchar(255)    NOT NULL                            COMMENT '����������˾�Ļ�������',
    org_code        varchar(255)    NOT NULL                            COMMENT '����ֱ�������ı���',
    create_by       varchar(255)    NOT NULL                            COMMENT '������',
    create_date     datetime        NOT NULL                            COMMENT '����ʱ��',
    update_by       varchar(255)    NOT NULL                            COMMENT '������',
    update_date     datetime        NOT NULL                            COMMENT '����ʱ��',
    remarks         varchar(255)                                        COMMENT '��ע��Ϣ',
    del_flag        char(1)         NOT NULL    DEFAULT '1'             COMMENT 'ɾ����ǣ�1��������2��ɾ����3����ˣ�',
    uuid            varchar(36)     NOT NULL                            COMMENT '����ȫ�ֱ�ʶUUID',

    project_name    varchar(100)    NOT NULL                            COMMENT '�ĵ���Ŀ����-����doc_project(�ĵ���Ŀ��)',
    parent_id       bigint          NOT NULL                            COMMENT '�������,���ڵ�ĸ�������ǣ�-1',
    full_path       varchar(255)    NOT NULL    UNIQUE                  COMMENT '���ṹ��ȫ·���á�-������,�����Լ���ID',
    title           varchar(100)    NOT NULL                            COMMENT '�ĵ������½ڵı���',
    content         MediumText      NOT NULL                            COMMENT '�ĵ�����',
    is_directory    char(1)         NOT NULL                            COMMENT '�Ƿ���Ŀ¼��0����1���ǣ�',
    PRIMARY KEY (id)
) COMMENT = '�ĵ���';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    doc_history -- �ĵ���ʷ��
==================================================================================================================== */
CREATE TABLE doc_history
(
    id              bigint          NOT NULL    auto_increment          COMMENT '���',
    create_by       varchar(255)    NOT NULL                            COMMENT '������',
    create_date     datetime        NOT NULL                            COMMENT '����ʱ��',
    document_id     bigint          NOT NULL                            COMMENT '�ĵ�ID-����doc_document(�ĵ���)',
    content         MediumText      NOT NULL                            COMMENT '�ĵ�����',
    PRIMARY KEY (id)
) COMMENT = '�ĵ���ʷ��';
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/







