package org.cleverframe.common.utils;

import org.apache.commons.lang3.Conversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象类型转换工具类，使用类型强制转换实现<br/>
 * 1.把一种类型转换成另一种类型<br/>
 * 2.
 *
 * @author LiZhiWei
 * @version 2015年6月21日 上午11:32:12
 */
public class ConversionUtils extends Conversion {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ConversionUtils.class);

    /**
     * 把一种类型，通过类型强制转换成另一种类型，转换失败抛出异常<br/>
     *
     * @param object 待转换的数据
     */
    @SuppressWarnings("unchecked")
    public static <E> E converter(Object object) {
        return (E) object;
    }

    /**
     * 把一种类型，通过类型强制转换成另一种类型，转换失败不会抛出异常<br/>
     * 1.注意：defaultValue与返回值类型一定要一致<br/>
     *
     * @param object       待转换的数据
     * @param defaultValue 转换失败返回的默认值
     */
    @SuppressWarnings("unchecked")
    public static <E> E converter(Object object, E defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return (E) object;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return defaultValue;
        }
    }
}