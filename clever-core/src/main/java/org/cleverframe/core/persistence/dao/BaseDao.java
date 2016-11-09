package org.cleverframe.core.persistence.dao;

import org.cleverframe.common.reflection.ReflectionsUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.user.IUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 日志对象
     */
    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    /**
     * 用户信息获取接口
     */
    private static IUserUtils userUtils;

    /**
     * 返回用户信息获取接口
     * @return 获取失败返回null
     */
    public static IUserUtils getUserUtils() {
        if (userUtils == null) {
            userUtils = SpringContextHolder.getBean(SpringBeanNames.UserUtils);
        }
        if (userUtils == null) {
            RuntimeException exception = new RuntimeException("### IUserUtils注入失败,BeanName=[" + SpringBeanNames.UserUtils + "]");
            logger.error(exception.getMessage(), exception);
        } else {
            logger.debug("### IUserUtils注入成功,BeanName=[" + SpringBeanNames.UserUtils + "]");
        }
        return userUtils;
    }

    /**
     * HibernateDao工具类
     */
    protected HibernateDao<T> hibernateDao;

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
