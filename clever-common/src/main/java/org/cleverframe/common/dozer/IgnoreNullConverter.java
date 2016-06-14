package org.cleverframe.common.dozer;

import org.dozer.DozerConverter;

/**
 * 自定义的dozer转换器,忽略空值(null或空字符串)<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-14 15:59 <br/>
 */
public class IgnoreNullConverter extends DozerConverter<Object, Object> {

    public IgnoreNullConverter() {
        super(Object.class, Object.class);
    }

    @Override
    public Object convertTo(Object source, Object destination) {
        return getObject(source, destination);
    }

    @Override
    public Object convertFrom(Object source, Object destination) {
        return getObject(source, destination);
    }

    private Object getObject(Object source, Object destination) {
        if (source != null) {
            return source;
        } else {
            return destination;
        }
    }
}
