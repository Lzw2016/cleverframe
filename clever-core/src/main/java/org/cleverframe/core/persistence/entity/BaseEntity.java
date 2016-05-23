package org.cleverframe.core.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 实体类基类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 9:26 <br/>
 */
@SuppressWarnings("unused")
@MappedSuperclass
public abstract class BaseEntity implements Lifecycle, Serializable {
    /**
     * 表示显示的常值
     */
    public static final char SHOW = '1';
    /**
     * 表示隐藏的常值
     */
    public static final char HIDE = '0';

    /**
     * 是
     */
    public static final char YES = '1';
    /**
     * 否
     */
    public static final char NO = '0';

    /**
     * 删除标记名称
     */
    public static final String FIELD_DEL_FLAG = "delFlag";
    /**
     * 删除标记,1：正常
     */
    public static final char DEL_FLAG_NORMAL = '1';
    /**
     * 删除标记,2：删除
     */
    public static final char DEL_FLAG_DELETE = '2';
    /**
     * 删除标记,3：审核
     */
    public static final char DEL_FLAG_AUDIT = '3';

    /**
     * 自身关联实体类的fullPath属性分隔标识
     */
    public static final char FULL_PATH_SPLIT = '-';

    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_ALL = '1';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_COMPANY_AND_CHILD = '2';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_COMPANY = '3';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_ORG_AND_CHILD = '4';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_ORG = '5';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_SELF = '8';
    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在机构及以下数据；5：所在机构数据；8：仅本人数据；9：按明细设置）
     */
    public static final char DATA_SCOPE_CUSTOM = '9';

    /**
     * 不能直接使用此属性，使用前确保调用了getUserUtils()
     *
     * @see #getUserUtils()
     */
//    @JsonIgnore
//    @Transient
//    private IUserUtils userUtils;

    /**
     * 日志对象
     */
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(BaseEntity.class);

    /**
     * 获取用户工具
     */
//    public IUserUtils getUserUtils() {
//        if (userUtils == null) {
//            userUtils = SpringContextHolder.getBean(SysBeanNames.UserUtils);
//        }
//        return userUtils;
//    }

    /*在实体对象Save/Insert操作之前触发*/
    @Override
    public boolean onSave(Session session) throws CallbackException {
        // logger.debug("BaseEntity--onSave");
        return Lifecycle.NO_VETO;
    }

    /*在Session.update()操作之前触发*/
    @Override
    public boolean onUpdate(Session session) throws CallbackException {
        // logger.debug("BaseEntity--onUpdate");
        return Lifecycle.NO_VETO;
    }

    /*在实体对象Delete操作之前触发*/
    @Override
    public boolean onDelete(Session session) throws CallbackException {
        // logger.debug("BaseEntity--onDelete");
        return Lifecycle.NO_VETO;
    }

    /*在实体对象被加载之后触发*/
    @Override
    public void onLoad(Session session, Serializable id) {
        // logger.debug("BaseEntity--onLoad");
    }
}
