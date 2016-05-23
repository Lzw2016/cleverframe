package org.cleverframe.common.utils;

/**
 * 字符串操作类工具，继承org.apache.commons.lang3.StringUtils<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-28 0:20 <br/>
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 调用对象的toString方法，如果对象为空返回默认值
     *
     * @param object     需要toString的对象
     * @param defaultStr 对象为空时返回的默认值
     * @return 返回对象的toString方法结果
     */
    public String objectToString(Object object, String defaultStr) {
        if (null == object) {
            return defaultStr;
        } else {
            return object.toString();
        }
    }
}
