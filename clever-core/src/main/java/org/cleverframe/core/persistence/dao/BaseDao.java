package org.cleverframe.core.persistence.dao;

import org.cleverframe.common.reflection.ReflectionsUtils;

import java.io.Serializable;

/**
 * Dao基类，包含HibernateDao工具类<br/>
 * <b>注意：若继承该类并重写构造方法的话一定要调用super()</b><br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-14 0:06 <br/>
 *
 * @param <T> 实体类类型，特定Dao继承此类时必须指明泛型T的具体类型
 */
public abstract class BaseDao<T extends Serializable> {
    /**
     * HibernateDao工具类
     */
    protected HibernateDao<T> hibernateDao;

//    /**
//     * IUserUtils方便获取当前用户的组织架构信息
//     */
//    @Autowired
//    @Qualifier(SysBeanNames.UserUtils)
//    protected IUserUtils userUtils;

    /**
     * 默认构造，初始化HibernateDao工具类
     */
    public BaseDao() {
        Class<T> entityClass = ReflectionsUtils.getClassGenricType(getClass());
        hibernateDao = new HibernateDao<>(entityClass);
    }

    /**
     * @return 获取HibernateDao工具类
     */
    public HibernateDao<T> getHibernateDao() {
        return hibernateDao;
    }
}
