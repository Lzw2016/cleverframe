package org.cleverframe.common.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * Jackson的ObjectMapper的配置和设置便捷操作工具<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-29 16:55 <br/>
 */
public class JacksonMapperUtils {
    /**
     * 允许单引号<br/>
     * 允许不带引号的字段名称<br/>
     */
    public static ObjectMapper enableSimple(ObjectMapper mapper) {
        if (null != mapper) {
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        }
        return mapper;
    }

    /**
     * 设定是否使用枚举的的toString函数来读写枚举，<br/>
     * 为False时使用Enum的name()函数来读写Enum，默认为false<br/>
     * 注意本函数一定要在Mapper创建后，所有的读写动作之前调用。<br/>
     */
    public static ObjectMapper enableEnumUseToString(ObjectMapper mapper) {
        if (null != mapper) {
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        }
        return mapper;
    }

    /**
     * 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合。<br/>
     * 默认会先查找jaxb的annotation，如果找不到再找jackson的。<br/>
     */
    public static ObjectMapper enableJaxbAnnotation(ObjectMapper mapper) {
        if (null != mapper) {
            JaxbAnnotationModule module = new JaxbAnnotationModule();
            mapper.registerModule(module);
        }
        return mapper;
    }
}
