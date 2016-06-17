package org.cleverframe.core.persistence.entity;

import java.io.Serializable;

/**
 * 实体类接口<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 9:26 <br/>
 */
@SuppressWarnings("unused")
public interface BaseEntity extends Serializable {
    /**
     * 表示显示的常值
     */
    Character SHOW = '1';
    /**
     * 表示隐藏的常值
     */
    Character HIDE = '0';

    /**
     * 是
     */
    Character YES = '1';
    /**
     * 否
     */
    Character NO = '0';

    /**
     * 删除标记名称
     */
    String FIELD_DEL_FLAG = "delFlag";
    /**
     * 删除标记,1：正常
     */
    Character DEL_FLAG_NORMAL = '1';
    /**
     * 删除标记,2：删除
     */
    Character DEL_FLAG_DELETE = '2';
    /**
     * 删除标记,3：审核
     */
    Character DEL_FLAG_AUDIT = '3';

    /**
     * 自身关联实体类的fullPath属性分隔标识
     */
    Character FULL_PATH_SPLIT = '-';

    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_ALL = '1';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_COMPANY_AND_CHILD = '2';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_COMPANY = '3';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_ORG_AND_CHILD = '4';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_ORG = '5';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_SELF = '8';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    Character DATA_SCOPE_CUSTOM = '9';
}
