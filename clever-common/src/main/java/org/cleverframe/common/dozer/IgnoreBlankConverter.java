package org.cleverframe.common.dozer;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;

/**
 * 自定义的dozer转换器,忽略String类型的空值(null或空字符串)<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-14 15:44 <br/>
 */
public class IgnoreBlankConverter extends DozerConverter<String, String> {

    public IgnoreBlankConverter() {
        super(String.class, String.class);
    }

    @Override
    public String convertTo(String source, String destination) {
        return getObject(source, destination);
    }

    @Override
    public String convertFrom(String source, String destination) {
        return getObject(source, destination);
    }

    private String getObject(String source, String destination) {
        if (StringUtils.isNotBlank(source)) {
            return source;
        } else {
            return destination;
        }
    }
}
