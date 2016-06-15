package org.cleverframe.core.persistence.dao;

import org.apache.commons.lang3.math.NumberUtils;
import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.common.reflection.ReflectionsUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.HqlParserUtils;
import org.cleverframe.common.utils.JavaBeanUtils;
import org.cleverframe.common.utils.SqlParserUtils;
import org.cleverframe.core.persistence.entity.BaseEntity;
import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 使用Hibernate框架实现的Dao工具类<br/>
 * 1.Hibernate相关操作：得到Hibernate的Session、得到HibernateTemplate、操作Session缓存<br/>
 * 2.DAO基本操作支持：增删查改<br/>
 * 3.HQL操作支持<br/>
 * 4.SQL操作支持<br/>
 * 5.Criteria操作支持<br/>
 * TODO 新增Hibernate Search的支持（考虑使用新的类实现）<br/>
 * <b>注意：若继承该类并重写构造方法的话一定要调用super()</b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-13 23:01 <br/>
 *
 * @param <T> 实体类类型，特定Dao继承此类时必须指明泛型T的具体类型
 */
public class HibernateDao<T extends Serializable> {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(HibernateDao.class);

    /**
     * Spring提供的Hibernate的模版类
     */
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    @Qualifier(SpringBeanNames.HibernateTemplate)
    private HibernateTemplate hibernateTemplate;

    /**
     * 实体类类型(由构造方法自动赋值)
     */
    private Class<T> entityClass;

    /**
     * 构造方法，根据实例类自动获取实体类类型<br/>
     * <b>注意：使用new创建当前类实例时，不能使用此构造，应使用：HibernateDao(Class entityClass)</b>
     *
     * @see #HibernateDao(Class entityClass)
     */
    public HibernateDao() {
        entityClass = ReflectionsUtils.getClassGenricType(getClass());
    }

    /**
     * 提供使用new创建此类实例的构造，必须指定entityClass值<br/>
     *
     * @param entityClass 实体类类型
     */
    public HibernateDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * @return 返回当前Dao对应的实体类型
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 设置查询参数<br/>
     *
     * @param query     Hibernate查询对象
     * @param parameter 查询参数对象
     */
    private void setParameter(Query query, Parameter parameter) {
        if (parameter != null) {
            Set<String> keySet = parameter.keySet();
            for (String string : keySet) {
                Object value = parameter.get(string);
                // 这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if (value instanceof Collection<?>) {
                    query.setParameterList(string, (Collection<?>) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(string, (Object[]) value);
                } else {
                    query.setParameter(string, value);
                }
            }
        }
    }

    /**
     * 设置查询结果类型<br/>
     *
     * @param query       SQL查询对象
     * @param resultClass 设置Hibernate查询结果对象类型
     */
    private void setResultTransformer(SQLQuery query, Class<?> resultClass) {
        if (resultClass != null) {
            if (resultClass == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else if (resultClass == List.class) {
                query.setResultTransformer(Transformers.TO_LIST);
            } else {
                query.addEntity(resultClass);
            }
        }
    }

    // ----------------------------------------------------------------
    // Hibernate相关操作
    // ----------------------------------------------------------------

    /**
     * 返回HibernateTemplate对象
     */
    public HibernateTemplate getHibernateTemplate() {
        // 若直接new创建此类对象Spring无法注入hibernateTemplate实例
        if (hibernateTemplate == null) {
            hibernateTemplate = SpringContextHolder.getBean(SpringBeanNames.HibernateTemplate);
        }
        return hibernateTemplate;
    }

    /**
     * 获取当前线程的Hibernate的Session
     */
    public Session getSession() {
        return getHibernateTemplate().getSessionFactory().getCurrentSession();
    }

    /**
     * 刷新Hibernate一级缓存，强制与数据库同步
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 清空Hibernate一级缓存，清除缓存数据，取消未提交的更改
     */
    public void clear() {
        getSession().clear();
    }

    // ----------------------------------------------------------------
    // DAO基本操作支持
    // ----------------------------------------------------------------

    /**
     * 保存任意类型实体类对象
     *
     * @param entity 任意实体类对象
     * @return 返回已保存的实体对象
     */
    public <E extends Serializable> Serializable save(E entity) {
        return getSession().save(entity);
    }

    /**
     * 保存或更新任意类型实体类对象
     *
     * @param entity 任意实体类对象
     * @param <E>    实体类泛型
     */
    public <E extends Serializable> void saveOrUpdate(E entity) {
        getSession().saveOrUpdate(entity);
    }

    /**
     * 直接从数据库删除数据，慎用
     *
     * @param entity 任意实体类对象
     */
    public <E extends Serializable> void delete(E entity) {
        getSession().delete(entity);
    }

    /**
     * 直接从数据库删除数据，慎用
     *
     * @param id 当前Dao对应的实体类对象的ID
     * @return 更新记录数
     */
    public int deleteById(Serializable id) {
        String updateHql = String.format("delete from %s where id = :p1", entityClass.getSimpleName());
        return updateByHql(updateHql, new Parameter(id));
    }

    /**
     * 软删除实体类，实体类必须继承 DataEntity<br/>
     * <b>注意：只根据ID查询实体类，修改delFlag值然后更新，只会更新delFlag字段</b><br/>
     *
     * @param entity 实体类对象,ID必须有值
     * @param <E>    实体类泛型
     */
    public <E extends IdEntity> void deleteForSoft(E entity) {
        IdEntity idEntity = this.getEntity(entity.getClass(), entity.getId());
        idEntity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
        getSession().update(idEntity);
    }

    /**
     * 逻辑删除当前Dao对应的实体类对象<br/>
     *
     * @param id 当前Dao对应的实体类对象的ID
     * @return 更新记录数
     */
    public int deleteByIdForSoft(Serializable id) {
        String updateHql = String.format("update %s set delFlag='%s' where id = :p1", entityClass.getSimpleName(), BaseEntity.DEL_FLAG_DELETE);
        return updateByHql(updateHql, new Parameter(id));
    }

    /**
     * 更新任意类型实体类对象<br/>
     * <b>
     *     注意：该方法直接使用Hibernate的update方法，<br/>
     *     只适用于更新持久化状态的对象(已被持久化，并且在Session缓存中的实体类)
     * </b><br/>
     *
     * @param entity 任意实体类对象
     * @param <E>    实体类泛型
     */
    public <E extends Serializable> void update(E entity) {
        getSession().update(entity);
    }

    /**
     * 更新继承IdEntity的实体类对象,可以控制不更新空值字段(可能会在更新之前查询一次数据库)<br/>
     * <b>
     *     注意:只更新空值字段时entity参数的id必须有值<br/>
     * </b>
     *
     * @param entity           继承IdEntity的实体类对象,ID必须有值
     * @param updateNullField  是否更新null值字段，对所有字段有效
     * @param updateEmptyField 是否更新空值字段，只对String字段有效
     * @param <E>              实体类泛型
     */
    public <E extends IdEntity> void update(E entity, boolean updateNullField, boolean updateEmptyField) {
        if (updateNullField && updateEmptyField) {
            getSession().update(entity);
            return;
        }

        IdEntity idEntity = this.getEntity(entity.getClass(), entity.getId());
        if(idEntity == null) {
            logger.debug("### update更新数据不存在");
            return;
        }
        if (!JavaBeanUtils.copyTo(entity, idEntity, updateNullField, updateEmptyField)) {
            throw new RuntimeException("### update异常(动态更新,可以控制不更新空值字段)");
        }
        getSession().update(idEntity);
    }

    /**
     * 更新当前Dao对应的实体类对象的删除标记<br/>
     *
     * @param id      当前Dao对应的实体类对象的ID
     * @param delFlag 删除标记常量，参考：BaseEntity
     */
    public int updateDelFlag(Serializable id, String delFlag) {
        String updateHql = String.format("update %s set delFlag = :p2 where id = :p1", entityClass.getSimpleName());
        return updateByHql(updateHql, new Parameter(id, delFlag));
    }

    /**
     * 得到任意类型的实体类对象
     *
     * @param entityClass 设置返回对象类型
     * @param id          查询对象的ID
     * @param <E>         实体类泛型
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> E getEntity(Class<E> entityClass, Serializable id) {
        return (E) getSession().get(entityClass, id);
    }

    /**
     * 得到当前DAO类型的实体类对象
     *
     * @param id 当前Dao对应的实体类对象的ID
     * @return 当前Dao对应的实体类对象
     */
    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 得到当前Dao对应的实体类对象在数据库中的总数，含有软删的数据，就是对应表的所有数据
     */
    public long getTotalCount() {
        String hqlQuery = String.format("select count(*) from %s", entityClass.getName());
        return (Long) getSession().createQuery(hqlQuery).uniqueResult();
    }

    // ----------------------------------------------------------------
    // HQL操作支持
    // ----------------------------------------------------------------

    /**
     * 创建HQL查询对象，也可用于更新数据<br/>
     *
     * @param hqlQuery  HQL查询字符串
     * @param parameter 查询参数
     */
    public Query createHqlQuery(String hqlQuery, Parameter parameter) {
        Query query = getSession().createQuery(hqlQuery);
        setParameter(query, parameter);
        return query;
    }

    /**
     * 使用HQL查询一条数据，返回Map类型数据<br/>
     *
     * @param hqlQuery  HQL查询字符串
     * @param parameter 查询参数
     * @return 一条Map数据
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getMapByHql(String hqlQuery, Parameter parameter) {
        Query query = createHqlQuery(hqlQuery, parameter);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<Object, Object>) query.uniqueResult();
    }

    /**
     * 通过HQL查找一个实体对象，带参数<br/>
     *
     * @param hqlQuery  HQL查询字符串
     * @param parameter 查询参数
     * @return HQL查询字符串中对应的实体类对象
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> E getByHql(String hqlQuery, Parameter parameter) {
        Query query = createHqlQuery(hqlQuery, parameter);
        return (E) query.uniqueResult();
    }

    /**
     * 通过HQL查找一个实体对象，带参数<br/>
     *
     * @param hqlQuery HQL查询字符串
     * @param values   查询参数
     * @return HQL查询字符串中对应的实体类对象
     */
    public <E extends Serializable> E getByHql(String hqlQuery, Object... values) {
        return getByHql(hqlQuery, new Parameter(values));
    }

    /**
     * 通过HQL查找一个实体对象，带参数<br/>
     *
     * @param hqlQuery   HQL查询字符串
     * @param parameters 查询参数
     * @return HQL查询字符串中对应的实体类对象
     */
    public <E extends Serializable> E getByHql(String hqlQuery, Object[][] parameters) {
        return getByHql(hqlQuery, new Parameter(parameters));
    }

    /**
     * 通过HQL查找一个实体对象<br/>
     *
     * @param hqlQuery HQL查询字符串
     * @return HQL查询字符串中对应的实体类对象
     */
    public <E extends Serializable> E getByHql(String hqlQuery) {
        return getByHql(hqlQuery, (Parameter) null);
    }

    /**
     * 获取一条查询HQL查询数据的总数
     *
     * @param hqlQuery  HQL查询
     * @param parameter 查询参数
     * @return 查询结果总数
     */
    public long getCountByHql(String hqlQuery, Parameter parameter) {
        Query query = createHqlQuery(HqlParserUtils.hqlToCount(hqlQuery), parameter);
        Object count = query.uniqueResult();
        return NumberUtils.toLong(count.toString(), 0L);
    }

    /**
     * 获取一条查询HQL查询数据的总数
     *
     * @param hqlQuery HQL查询
     * @return 查询结果总数
     */
    public long getCountByHql(String hqlQuery) {
        return getCountByHql(hqlQuery, null);
    }

    /**
     * HQL查询数据，带参数<br/>
     *
     * @param hqlQuery  HQL查询字符串
     * @param parameter 查询参数
     * @return HQL查询字符串中对应的实体类对象集合
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> List<E> findByHql(String hqlQuery, Parameter parameter) {
        Query query = createHqlQuery(hqlQuery, parameter);
        return query.list();
    }

    /**
     * HQL查询数据，带参数<br/>
     *
     * @param hqlQuery HQL查询字符串
     * @param values   查询参数
     * @return HQL查询字符串中对应的实体类对象集合
     */
    public <E extends Serializable> List<E> findByHql(String hqlQuery, Object... values) {
        return findByHql(hqlQuery, new Parameter(values));
    }

    /**
     * HQL查询数据，带参数<br/>
     *
     * @param hqlQuery   HQL查询字符串
     * @param parameters 查询参数
     * @return HQL查询字符串中对应的实体类对象集合
     */
    public <E extends Serializable> List<E> findByHql(String hqlQuery, Object[][] parameters) {
        return findByHql(hqlQuery, new Parameter(parameters));
    }

    /**
     * HQL查询数据<br/>
     *
     * @param hqlQuery HQL查询字符串
     * @return HQL查询字符串中对应的实体类对象集合
     */
    public <E extends Serializable> List<E> findByHql(String hqlQuery) {
        return findByHql(hqlQuery, (Parameter) null);
    }

    /**
     * HQL更新数据，带参数<br/>
     *
     * @param hqlUpdate HQL更新语句
     * @param parameter 更新参数
     * @return 更新记录数
     */
    public int updateByHql(String hqlUpdate, Parameter parameter) {
        return createHqlQuery(hqlUpdate, parameter).executeUpdate();
    }

    /**
     * HQL更新数据，带参数<br/>
     *
     * @param hqlUpdate HQL更新语句
     * @param values    更新参数
     * @return 更新记录数
     */
    public int updateByHql(String hqlUpdate, Object... values) {
        return createHqlQuery(hqlUpdate, new Parameter(values)).executeUpdate();
    }

    /**
     * HQL更新数据，带参数<br/>
     *
     * @param hqlUpdate  HQL更新语句
     * @param parameters 更新参数
     * @return 更新记录数
     */
    public int updateByHql(String hqlUpdate, Object[][] parameters) {
        return createHqlQuery(hqlUpdate, new Parameter(parameters)).executeUpdate();
    }

    /**
     * HQL更新数据<br/>
     *
     * @param hqlUpdate HQL更新语句
     * @return 更新记录数
     */
    public int updateByHql(String hqlUpdate) {
        return updateByHql(hqlUpdate, (Parameter) null);
    }

    /**
     * HQL分页查询的实现，带参数<br/>
     *
     * @param page      分页对象
     * @param hqlQuery  HQL查询
     * @param parameter 查询参数
     * @return 分页对象，其实体类型是HQL查询中对应的实体类
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> Page<E> findByHql(Page<E> page, String hqlQuery, Parameter parameter) {
        // 得到数据的总数
        long count = getCountByHql(hqlQuery, parameter);
        page.setCount(count);
        if (count <= 0) {
            return page;
        }
        // 查询数据
        Query query = createHqlQuery(hqlQuery, parameter);
        // 设置分页数据
        if (!page.isDisabled()) {
            query.setFirstResult(page.getFirstResult());
            query.setMaxResults(page.getPageSize());
        }
        page.setList(query.list());
        return page;
    }

    /**
     * HQL分页查询的实现，带参数<br/>
     *
     * @param page     分页对象
     * @param hqlQuery HQL查询
     * @param values   查询参数
     * @return 分页对象，其实体类型是HQL查询中对应的实体类
     */
    public <E extends Serializable> Page<E> findByHql(Page<E> page, String hqlQuery, Object... values) {
        return findByHql(page, hqlQuery, new Parameter(values));
    }

    /**
     * HQL分页查询的实现，带参数<br/>
     *
     * @param page       分页对象
     * @param hqlQuery   HQL查询
     * @param parameters 查询参数
     * @return 分页对象，其实体类型是HQL查询中对应的实体类
     */
    public <E extends Serializable> Page<E> findByHql(Page<E> page, String hqlQuery, Object[][] parameters) {
        return findByHql(page, hqlQuery, new Parameter(parameters));
    }

    /**
     * HQL分页查询的实现，带参数<br/>
     *
     * @param page     分页对象
     * @param hqlQuery HQL查询
     * @return 分页对象，其实体类型是HQL查询中对应的实体类
     */
    public <E extends Serializable> Page<E> findByHql(Page<E> page, String hqlQuery) {
        return findByHql(page, hqlQuery, (Parameter) null);
    }

    // ----------------------------------------------------------------
    // SQL操作支持
    // ----------------------------------------------------------------

    /**
     * 创建 SQL 查询对象<br/>
     *
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return SQLQuery对象
     */
    public SQLQuery createSqlQuery(String sqlQuery, Parameter parameter) {
        SQLQuery query = getSession().createSQLQuery(sqlQuery);
        setParameter(query, parameter);
        return query;
    }

    /**
     * 使用SQL查询一条数据，返回Map类型数据<br/>
     *
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return 一条Map数据
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMapBySql(String sqlQuery, Parameter parameter) {
        Query query = createSqlQuery(sqlQuery, parameter);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) query.uniqueResult();
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @param parameter   查询参数
     * @return 与entityClass对应的实体类对象
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> E getBySql(Class<E> entityClass, String sqlQuery, Parameter parameter) {
        SQLQuery query = createSqlQuery(sqlQuery, parameter);
        setResultTransformer(query, entityClass);
        return (E) query.uniqueResult();
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @param values      查询参数
     * @return 与entityClass对应的实体类对象
     */
    public <E extends Serializable> E getBySql(Class<E> entityClass, String sqlQuery, Object... values) {
        return getBySql(entityClass, sqlQuery, new Parameter(values));
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @param parameters  查询参数
     * @return 与entityClass对应的实体类对象
     */
    public <E extends Serializable> E getBySql(Class<E> entityClass, String sqlQuery, Object[][] parameters) {
        return getBySql(entityClass, sqlQuery, new Parameter(parameters));
    }

    /**
     * 通过SQL查找一个实体对象<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @return 与entityClass对应的实体类对象
     */
    public <E extends Serializable> E getBySql(Class<E> entityClass, String sqlQuery) {
        return getBySql(entityClass, sqlQuery, (Parameter) null);
    }

    /**
     * 通过SQL查找一个实体对象<br/>
     *
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return 与当前Dao对应的实体类对象
     */
    public T getBySql(String sqlQuery, Parameter parameter) {
        return getBySql(entityClass, sqlQuery, parameter);
    }

    /**
     * 通过SQL查找一个实体对象<br/>
     *
     * @param sqlQuery SQL查询
     * @param values   查询参数
     * @return 与当前Dao对应的实体类对象
     */
    public T getBySql(String sqlQuery, Object... values) {
        return getBySql(entityClass, sqlQuery, new Parameter(values));
    }

    /**
     * 通过SQL查找一个实体对象<br/>
     *
     * @param sqlQuery   SQL查询
     * @param parameters 查询参数
     * @return 与当前Dao对应的实体类对象
     */
    public T getBySql(String sqlQuery, Object[][] parameters) {
        return getBySql(entityClass, sqlQuery, new Parameter(parameters));
    }

    /**
     * 通过SQL查找一个实体对象<br/>
     *
     * @param sqlQuery SQL查询
     * @return 与当前Dao对应的实体类对象
     */
    public T getBySql(String sqlQuery) {
        return getBySql(entityClass, sqlQuery, (Parameter) null);
    }

    /**
     * 获取一条查询SQL查询数据的总数
     *
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return 查询结果总数
     */
    public long getCountBySql(String sqlQuery, Parameter parameter) {
        // 得到数据的总数
        Query query = createSqlQuery(SqlParserUtils.getSmartCountSql(sqlQuery), parameter);
        Object count = query.uniqueResult();
        return NumberUtils.toLong(count.toString(), 0L);
    }

    /**
     * 获取一条查询SQL查询数据的总数
     *
     * @param sqlQuery SQL查询
     * @return 查询结果总数
     */
    public long getCountBySql(String sqlQuery) {
        return getCountBySql(sqlQuery, null);
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @param parameter   查询参数
     * @return 与entityClass对应的实体类对象集合
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> List<E> findBySql(Class<E> entityClass, String sqlQuery, Parameter parameter) {
        SQLQuery query = createSqlQuery(sqlQuery, parameter);
        setResultTransformer(query, entityClass);
        return query.list();
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @param values      查询参数
     * @return 与entityClass对应的实体类对象集合
     */
    public <E extends Serializable> List<E> findBySql(Class<E> entityClass, String sqlQuery, Object... values) {
        return findBySql(entityClass, sqlQuery, new Parameter(values));
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param entityClass 设置返回实体的类型
     * @param sqlQuery    SQL查询
     * @param parameters  查询参数
     * @return 与entityClass对应的实体类对象集合
     */
    public <E extends Serializable> List<E> findBySql(Class<E> entityClass, String sqlQuery, Object[][] parameters) {
        return findBySql(entityClass, sqlQuery, new Parameter(parameters));
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return 当前Dao对应的实体类对象集合
     */
    public List<T> findBySql(String sqlQuery, Parameter parameter) {
        return findBySql(entityClass, sqlQuery, parameter);
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param sqlQuery SQL查询
     * @param values   查询参数
     * @return 当前Dao对应的实体类对象集合
     */
    public List<T> findBySql(String sqlQuery, Object... values) {
        return findBySql(entityClass, sqlQuery, new Parameter(values));
    }

    /**
     * 通过SQL查找一个实体对象，带参数<br/>
     *
     * @param sqlQuery   SQL查询
     * @param parameters 查询参数
     * @return 当前Dao对应的实体类对象集合
     */
    public List<T> findBySql(String sqlQuery, Object[][] parameters) {
        return findBySql(entityClass, sqlQuery, new Parameter(parameters));
    }

    /**
     * 通过SQL查找一个实体对象，不带参数<br/>
     *
     * @param sqlQuery SQL查询
     * @return 当前Dao对应的实体类对象集合
     */
    public List<T> findBySql(String sqlQuery) {
        return findBySql(entityClass, sqlQuery, (Parameter) null);
    }

    /**
     * SQL 更新<br/>
     *
     * @param sqlUpdate SQL更新语句
     * @param parameter SQL参数
     * @return 更新记录数
     */
    public int updateBySql(String sqlUpdate, Parameter parameter) {
        return createSqlQuery(sqlUpdate, parameter).executeUpdate();
    }

    /**
     * SQL 更新<br/>
     *
     * @param sqlUpdate SQL更新语句
     * @param values    SQL参数
     * @return 更新记录数
     */
    public int updateBySql(String sqlUpdate, Object... values) {
        return createSqlQuery(sqlUpdate, new Parameter(values)).executeUpdate();
    }

    /**
     * SQL 更新<br/>
     *
     * @param sqlUpdate  SQL更新语句
     * @param parameters SQL参数
     * @return 更新记录数
     */
    public int updateBySql(String sqlUpdate, Object[][] parameters) {
        return createSqlQuery(sqlUpdate, new Parameter(parameters)).executeUpdate();
    }

    /**
     * SQL 更新<br/>
     *
     * @param sqlUpdate SQL更新语句
     * @return 更新记录数
     */
    public int updateBySql(String sqlUpdate) {
        return createSqlQuery(sqlUpdate, null).executeUpdate();
    }

    /**
     * SQL分页查询实现，返回分页Map数据<br/>
     *
     * @param page      分页对象
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return 分页对象
     */
    public Page<Map<Object, Object>> findMapBySql(Page<Map<Object, Object>> page, String sqlQuery, Parameter parameter) {
        // 得到数据的总数
        long count = getCountBySql(sqlQuery, parameter);
        page.setCount(count);
        if (count <= 0) {
            return page;
        }
        // 查询数据
        SQLQuery query = createSqlQuery(sqlQuery, parameter);
        // 设置分页数据
        if (!page.isDisabled()) {
            query.setFirstResult(page.getFirstResult());
            query.setMaxResults(page.getPageSize());
        }
        setResultTransformer(query, Map.class);
        @SuppressWarnings("unchecked")
        List<Map<Object, Object>> list = query.list();
        page.setList(list);
        return page;
    }

    /**
     * SQL分页查询实现<br/>
     *
     * @param entityClass 设置返回实体类类型
     * @param page        分页对象
     * @param sqlQuery    SQL查询
     * @param parameter   查询参数
     * @return 分页对象，其实体类是entityClass对应的实体类型
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> Page<E> findBySql(Class<E> entityClass, Page<E> page, String sqlQuery, Parameter parameter) {
        // 得到数据的总数
        long count = getCountBySql(sqlQuery, parameter);
        page.setCount(count);
        if (count <= 0) {
            return page;
        }
        // 查询数据
        SQLQuery query = createSqlQuery(sqlQuery, parameter);
        // 设置分页数据
        if (!page.isDisabled()) {
            query.setFirstResult(page.getFirstResult());
            query.setMaxResults(page.getPageSize());
        }
        setResultTransformer(query, entityClass);
        page.setList(query.list());
        return page;
    }

    /**
     * SQL分页查询实现<br/>
     *
     * @param entityClass 设置返回实体类类型
     * @param page        分页对象
     * @param sqlQuery    SQL查询
     * @param values      查询参数
     * @return 分页对象，其实体类是entityClass对应的实体类型
     */
    public <E extends Serializable> Page<E> findBySql(Class<E> entityClass, Page<E> page, String sqlQuery, Object... values) {
        return findBySql(entityClass, page, sqlQuery, new Parameter(values));
    }

    /**
     * SQL分页查询实现<br/>
     *
     * @param entityClass 设置返回实体类类型
     * @param page        分页对象
     * @param sqlQuery    SQL查询
     * @param parameters  查询参数
     * @return 分页对象，其实体类是entityClass对应的实体类型
     */
    public <E extends Serializable> Page<E> findBySql(Class<E> entityClass, Page<E> page, String sqlQuery, Object[][] parameters) {
        return findBySql(entityClass, page, sqlQuery, new Parameter(parameters));
    }

    /**
     * SQL分页查询实现<br/>
     *
     * @param entityClass 设置返回实体类类型
     * @param page        分页对象
     * @param sqlQuery    SQL查询
     * @return 分页对象，其实体类是entityClass对应的实体类型
     */
    public <E extends Serializable> Page<E> findBySql(Class<E> entityClass, Page<E> page, String sqlQuery) {
        return findBySql(entityClass, page, sqlQuery, (Parameter) null);
    }

    /**
     * SQL 分页查询<br/>
     *
     * @param page      分页对象
     * @param sqlQuery  SQL查询
     * @param parameter 查询参数
     * @return 分页对象，其实体类是对应Dao的实体类型
     */
    public Page<T> findBySql(Page<T> page, String sqlQuery, Parameter parameter) {
        return findBySql(entityClass, page, sqlQuery, parameter);
    }

    /**
     * SQL 分页查询<br/>
     *
     * @param page     分页对象
     * @param sqlQuery SQL查询
     * @param values   查询参数
     * @return 分页对象，其实体类是对应Dao的实体类型
     */
    public Page<T> findBySql(Page<T> page, String sqlQuery, Object... values) {
        return findBySql(entityClass, page, sqlQuery, new Parameter(values));
    }

    /**
     * SQL 分页查询<br/>
     *
     * @param page       分页对象
     * @param sqlQuery   SQL查询
     * @param parameters 查询参数
     * @return 分页对象，其实体类是对应Dao的实体类型
     */
    public Page<T> findBySql(Page<T> page, String sqlQuery, Object[][] parameters) {
        return findBySql(entityClass, page, sqlQuery, new Parameter(parameters));
    }

    /**
     * SQL分页查询实现<br/>
     *
     * @param page     分页对象
     * @param sqlQuery SQL查询
     * @return 分页对象，其实体类是对应Dao的实体类型
     */
    public Page<T> findBySql(Page<T> page, String sqlQuery) {
        return findBySql(entityClass, page, sqlQuery, (Parameter) null);
    }

    // ----------------------------------------------------------------
    // Criteria操作支持，Criteria操作只支持返回当前DAO对应的Entity类型
    // ----------------------------------------------------------------

    /**
     * 创建与会话无关的检索标准对象，Criteria操作只支持返回当前DAO对应的Entity类型
     *
     * @param criterions Restrictions.eq("name", value);
     * @return DetachedCriteria对象
     */
    public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        for (Criterion c : criterions) {
            dc.add(c);
        }
        return dc;
    }

    /**
     * 使用检索标准对象查询，Criteria操作只支持返回当前DAO对应的Entity类型
     */
    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setResultTransformer(resultTransformer);
        return criteria.list();
    }

    /**
     * 使用检索标准对象查询，Criteria操作只支持返回当前DAO对应的Entity类型
     */
    public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
        return findByCriteria(detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
    }

    /**
     * 使用检索标准对象查询记录数，Criteria操作只支持返回当前DAO对应的Entity类型
     */
    @SuppressWarnings("rawtypes")
    public long count(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        long totalCount;
        try {
            // Get orders
            Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
            field.setAccessible(true);
            List orderEntrys = (List) field.get(criteria);
            // Remove orders
            field.set(criteria, new ArrayList());
            // Get count
            criteria.setProjection(Projections.rowCount());
            totalCount = Long.valueOf(criteria.uniqueResult().toString());
            // Clean count
            criteria.setProjection(null);
            // Restore orders
            field.set(criteria, orderEntrys);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.unchecked(e);
        }
        return totalCount;
    }

    /**
     * 使用检索标准对象分页查询，Criteria操作只支持返回当前DAO对应的Entity类型
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByCriteria(Page<T> page, DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
        // 得到数据的总数
        if (!page.isDisabled() && !page.isNotCount()) {
            page.setCount(count(detachedCriteria));
            if (page.getCount() < 1) {
                return page;
            }
        }
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setResultTransformer(resultTransformer);
        // 设置分页查询数据
        if (!page.isDisabled()) {
            criteria.setFirstResult(page.getFirstResult());
            criteria.setMaxResults(page.getPageSize());
        }
        // order by
        // if (StringUtils.isNotBlank(page.getOrderBy()))
        // {
        // for (String order : StringUtils.split(page.getOrderBy(), ","))
        // {
        // String[] o = StringUtils.split(order, " ");
        // if (o.length == 1)
        // {
        // criteria.addOrder(Order.asc(o[0]));
        // }
        // else if (o.length == 2)
        // {
        // if ("DESC".equals(o[1].toUpperCase()))
        // {
        // criteria.addOrder(Order.desc(o[0]));
        // }
        // else
        // {
        // criteria.addOrder(Order.asc(o[0]));
        // }
        // }
        // }
        // }
        page.setList(criteria.list());
        return page;
    }

    /**
     * 使用检索标准对象分页查询，Criteria操作只支持返回当前DAO对应的Entity类型
     */
    public Page<T> findByCriteria(Page<T> page, DetachedCriteria detachedCriteria) {
        return findByCriteria(page, detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
    }

    /**
     * 分页查询，Criteria操作只支持返回当前DAO对应的Entity类型
     */
    public Page<T> findByCriteria(Page<T> page) {
        return findByCriteria(page, createDetachedCriteria());
    }
}
