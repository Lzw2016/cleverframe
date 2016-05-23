package org.cleverframe.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaBean工具，支持通过反射对JavaBean各种操作<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-1 20:36 <br/>
 */
public class JavaBeanUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(JavaBeanUtils.class);

    /**
     * 获取对象属性值，支持以下场景：<br/>
     * 1.直接根据属性名获取：getProperty(myBean,"code")<br/>
     * 2.获取内嵌对象的属性：getProperty(orderBean, "address.city")<br/>
     * 3.还支持List和Map类型的属性：getProperty(orderBean, "customers[1].name")<br/>
     *
     * @param bean 对象
     * @param name 对象属性名，支持多场景
     * @param <T>  对象属性类型
     * @return 对象属性值，失败返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object bean, String name) {
        try {
            return (T) PropertyUtils.getProperty(bean, name);
        } catch (Throwable e) {
            logger.error("获取Bean属性失败", e);
            return null;
        }
    }

    /**
     * 设置对象属性值，支持以下场景：<br/>
     * 1.直接根据属性名获取：setProperty(myBean,"code",value)<br/>
     * 2.获取内嵌对象的属性：setProperty(orderBean, "address.city",value)<br/>
     * 3.还支持List和Map类型的属性：setProperty(orderBean, "customers[1].name",value)<br/>
     *
     * @param bean  对象
     * @param name  对象属性名，支持多场景
     * @param value 对象属性值
     * @return 成功返回true，失败返回false
     */
    public static boolean setProperty(Object bean, String name, Object value) {
        try {
            PropertyUtils.setProperty(bean, name, value);
            return true;
        } catch (Throwable e) {
            logger.error("设置Bean属性值失败", e);
            return false;
        }
    }

    /**
     * 获取对象属性类型，支持以下场景：<br/>
     * 1.直接根据属性名获取：getPropertyType(myBean,"code")<br/>
     * 2.获取内嵌对象的属性：getPropertyType(orderBean, "address.city")<br/>
     * 3.还支持List和Map类型的属性：getPropertyType(orderBean, "customers[1].name")<br/>
     *
     * @param bean 对象
     * @param name 对象属性名，支持多场景
     * @return 返回对象属性类型，失败返回null
     */
    public static Class getPropertyType(Object bean, String name) {
        try {
            return PropertyUtils.getPropertyType(bean, name);
        } catch (Throwable e) {
            logger.error("获取Bean属性类型失败", e);
            return null;
        }
    }

    /**
     * 反射调用构造方法，创建一个JavaBean对象<br/>
     *
     * @param clzz JavaBean类型
     * @param args 构造函数参数
     * @param <T>  JavaBean类型
     * @return 成功返回Bean对象，失败返回null
     */
    public static <T> T newObject(Class<T> clzz, Object... args) {
        try {
            return ConstructorUtils.invokeConstructor(clzz, args);
        } catch (Throwable e) {
            logger.error("创建Bean实例失败", e);
            return null;
        }
    }

    /**
     * 动态调用Java对象方法
     *
     * @param bean       Java对象
     * @param methodName 方法名
     * @param parameter  调用参数
     * @param <T>        返回值类型
     * @return 成功返回调用结果，失败返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object bean, String methodName, Object... parameter) {
        try {
            return (T) MethodUtils.invokeMethod(bean, methodName, parameter);
        } catch (Throwable e) {
            logger.error("动态调用Bean方法失败", e);
            return null;
        }
    }

    /**
     * 对象深clone，使用序列化方式实现
     *
     * @param bean JavaBean对象
     * @param <T>  JavaBean类型
     * @return 返回深clone之后的新的Java对象，失败返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(Object bean) {
        try {
            String json = JSON.toJSONString(bean);
            return (T) JSON.toJavaObject(JSON.parseObject(json), bean.getClass());
        } catch (Throwable e) {
            logger.error("深克隆Bean实例失败", e);
            return null;
        }
    }
}
