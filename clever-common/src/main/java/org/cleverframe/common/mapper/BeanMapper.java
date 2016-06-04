package org.cleverframe.common.mapper;

import com.google.common.collect.Lists;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * JavaBean与JavaBean之间的转换，使用org.dozer.DozerBeanMapper实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-30 15:24 <br/>
 */
public class BeanMapper {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(BeanMapper.class);

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static final DozerBeanMapper DOZER_BEAN_MAPPER;

    static {
        DozerBeanMapper dozerBeanMapper = SpringContextHolder.getBean(SpringBeanNames.DozerBeanMapper);
        if(dozerBeanMapper == null) {
            DOZER_BEAN_MAPPER = new DozerBeanMapper();
            logger.warn("### DozerBeanMapper注入失败, 请配置spring-context-dozer.xml文件");
        } else {
            DOZER_BEAN_MAPPER = dozerBeanMapper;
            logger.info("### DozerBeanMapper注入成功");
        }
    }

    private BeanMapper() {
    }

    /**
     * 不同类型的JavaBean对象转换<br/>
     *
     * @param source           数据源JavaBean
     * @param destinationClass 需要转换之后的JavaBean类型
     * @param <T>              需要转换之后的JavaBean类型
     * @return 转换之后的对象，失败返回null
     */
    public static <T> T mapper(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        try {
            return DOZER_BEAN_MAPPER.map(source, destinationClass);
        } catch (Throwable e) {
            logger.error("DozerBeanMapper 转换JavaBean失败", e);
            return null;
        }
    }

    /**
     * 不同类型的JavaBean对象转换，基于Collection(集合)的批量转换<br/>
     * 如：List&lt;ThisBean&gt; <---->  List&lt;OtherBean&gt;<br/>
     *
     * @param sourceList       数据源JavaBean集合
     * @param destinationClass 需要转换之后的JavaBean类型
     * @param <T>              需要转换之后的JavaBean类型
     * @return 转换之后的对象集合，失败返回null
     */
    public static <T> List<T> mapperCollection(Collection sourceList, Class<T> destinationClass) {
        if (sourceList == null) {
            return null;
        }
        try {
            List<T> destinationList = Lists.newArrayList();
            for (Object sourceObject : sourceList) {
                T destinationObject = DOZER_BEAN_MAPPER.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }
            return destinationList;
        } catch (Throwable e) {
            logger.error("DozerBeanMapper 转换Collection<JavaBean>失败", e);
            return null;
        }
    }

    /**
     * 将对象source的值拷贝到对象destinationObject中<br/>
     * <b>注意：此操作source中的属性会覆盖destinationObject中的属性，无论source中的属性是不是空的</b><br/>
     *
     * @param source            数据源对象
     * @param destinationObject 目标对象
     * @return 成功返回true，失败返回false
     */
    public static boolean copyTo(Object source, Object destinationObject) {
        try {
            DOZER_BEAN_MAPPER.map(source, destinationObject);
            return true;
        } catch (Throwable e) {
            logger.error("DozerBeanMapper 转换对象拷贝属性值失败", e);
            return false;
        }
    }
}
