package org.cleverframe.core.hibernate;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Hibernate的拦截器<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-11 23:11 <br/>
 */
public class EntityInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 1L;
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(EntityInterceptor.class);

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.debug("####Hibernate的拦截器onSave 将在对象被保存之前调用，这提供了一个对待保存对象属性进行修改的机会");
        return super.onSave(entity, id, state, propertyNames, types);
    }
/*
  //对象初始化之前加载，这里的entity处于刚被创建的状态（也就是说属性均未赋值）
  public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException;
  //Session.flush方法进行脏数据检查时，如果发现PO状态改变，则调用此方法
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException;
  //将在对象被保存之前调用，这提供了一个对待保存对象属性进行修改的机会
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException;
  //将在对象被删除之前调用
  public boolean onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException;
  //Session执行flush方法之前被调用
  public void preFlush(Iterator entities) throws CallbackException;
  //Session执行flush方法之后被调用
  public void postFlush(Iterator entities) throws CallbackException;
  //Session.saveOrUpdate方法时，将调用此方法判断对象是否尚未保存
  public Boolean isUnsaved(Object entity);
  //Session.flush方法时，将调用此方法判断对象是否为脏数据，这提供了脏数据检查的另一个拦截式实现渠道
  public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types);
  //用于创建实体对象时，如果返回null，则Hibernate将调用实体类的默认构造方法创建实体对象
  public Object instantiate(Class clazz, Serializable id) throws CallbackException;
  //集合更新之前调用
  public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException
*/
}
