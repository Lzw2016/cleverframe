package org.cleverframe.common.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;

/**
 * Json串与Java对象的相互转换工具<br/>
 * 1.通过Jackson实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-28 0:55 <br/>
 */
public class JacksonMapper {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(JacksonMapper.class);

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper，建议在外部接口中使用<br/>
     */
    private static final JacksonMapper NON_EMPTY_MAPPER;

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper，最节约的存储方式，建议在内部接口中使用<br/>
     */
    private static final JacksonMapper NON_DEFAULT_MAPPER;

    /**
     * 创建单例的JacksonMapper，使用全局共享的ObjectMapper对象，节省内存空间<br/>
     * 可以根据需求增加<br/>
     */
    static {
        NON_EMPTY_MAPPER = new JacksonMapper(Include.NON_EMPTY);

        NON_DEFAULT_MAPPER = new JacksonMapper(Include.NON_DEFAULT);
    }

    /**
     * 对象转换器
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 构造函数创建新的ObjectMapper
     *
     * @param include 设置Include属性
     */
    private JacksonMapper(Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 允许单引号、允许不带引号的字段名称
        JacksonMapperUtils.enableSimple(mapper);
        // 空值处理为空串
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        });
        // 进行HTML解码。
        mapper.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>() {
            @Override
            public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString(StringEscapeUtils.unescapeHtml4(value));
            }
        }));
        // 设置时区 getTimeZone("GMT+8:00")
        mapper.setTimeZone(TimeZone.getDefault());
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper，建议在外部接口中使用<br/>
     *
     * @return 返回的是一个单例JacksonMapper全局共享
     */
    public static JacksonMapper nonEmptyMapper() {
        return NON_EMPTY_MAPPER;
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper，最节约的存储方式，建议在内部接口中使用<br/>
     *
     * @return 返回的是一个单例JacksonMapper全局共享
     */
    public static JacksonMapper nonDefaultMapper() {
        return NON_DEFAULT_MAPPER;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".<br/>
     *
     * @param object 需要序列化的对象
     * @return 序列化后的Json字符串，失败返回null
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 输出JSON格式数据.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 反序列化POJO或简单Collection如List&lt;String&gt;<br/>
     * 如果JSON字符串为null或空字符串, 返回null. 如果JSON字符串为"[]", 返回空集合<br/>
     * 如需反序列化复杂Collection如List&lt;MyBean&gt;，请使用fromJson(String, JavaType)<br/>
     *
     * @param jsonString Json字符串
     * @param clazz      反序列化的对象类型
     * @return 反序列化的对象，失败返回null
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List&lt;MyBean&gt;<br/>
     * 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.<br/>
     *
     * @param jsonString Json字符串
     * @param javaType   JavaType
     * @return 反序列化的对象，失败返回null
     * @see #contructCollectionType(Class, Class)
     * @see #contructMapType(Class, Class, Class)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造Collection类型.
     *
     * @param collectionClass 集合类型
     * @param elementClass    集合泛型类型
     * @return 返回JavaType
     * @see #fromJson(String, JavaType)
     */
    @SuppressWarnings("rawtypes")
    public JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     *
     * @param mapClass   Map集合类型
     * @param keyClass   Map的Key泛型类型
     * @param valueClass Map的Value泛型类型
     * @return 返回JavaType
     * @see #fromJson(String, JavaType)
     */
    @SuppressWarnings("rawtypes")
    public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
     *
     * @param jsonString Json字符串
     * @param object     需要更新的对象
     * @return 操作成功返回true
     */
    public boolean update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            logger.error("update json string:" + jsonString + " to object:" + object + " error.", e);
            return false;
        } catch (IOException e) {
            logger.error("update json string:" + jsonString + " to object:" + object + " error.", e);
            return false;
        }
    }
}
