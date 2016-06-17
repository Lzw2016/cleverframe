package org.cleverframe.core.persistence.entity;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 含有基本字段和主键字段的实体类抽象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 9:25 <br/>
 */
@MappedSuperclass
public abstract class IdEntity extends DataEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 编号，Entity主键 ，使用统一的主键生成策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /*
     * 在实体对象Save/Insert操作之前触发
     * 持久化前操作<br/>
     * 1.生成ID值作为数据库主键<br/>
     * */
    @Override
    public boolean onSave(Session session) throws CallbackException {
        // TODO IdEntity 持久化前操作
        super.onSave(session);
        // logger.debug("IdEntity--onSave");
        return Lifecycle.NO_VETO;
    }

    /*在Session.update()操作之前触发*/
    @Override
    public boolean onUpdate(Session session) throws CallbackException {
        super.onUpdate(session);
        // logger.debug("IdEntity--onUpdate");
        return Lifecycle.NO_VETO;
    }

    /*在实体对象Delete操作之前触发*/
    @Override
    public boolean onDelete(Session session) throws CallbackException {
        super.onDelete(session);
        // logger.debug("IdEntity--onDelete");
        return Lifecycle.NO_VETO;
    }

    /*在实体对象被加载之后触发*/
    @Override
    public void onLoad(Session session, Serializable id) {
        super.onLoad(session, id);
        // logger.debug("IdEntity--onLoad");
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
