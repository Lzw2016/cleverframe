package org.cleverframe.common.mapper;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.utils.JsonTypeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json数据与XML数据相互转换的工具类，使用org.json实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-1 13:03 <br/>
 */
public class JsonXmlConverter {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(JsonXmlConverter.class);

    /**
     * XML字符串转换成Json字符串<br/>
     *
     * @param xml XML字符串
     * @return Json字符串，转换失败返回null
     */
    public static String xmlToJson(String xml) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        try {
            JSONObject jsonObject = XML.toJSONObject(xml);
            return jsonObject.toString();
        } catch (Throwable e) {
            logger.error("XML字符串转换成Json字符串失败", e);
            return null;
        }
    }

    /**
     * Json字符串转换成XML字符串<br/>
     *
     * @param json Json字符串
     * @return XML字符串，转换失败返回null
     */
    public static String jsonToXml(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            Object object;
            String jsonType = JsonTypeUtils.getJSONType(json);
            if (JsonTypeUtils.JSON_OBJECT.equals(jsonType)) {
                object = new JSONObject(json);
            } else if (JsonTypeUtils.JSON_ARRAY.equals(jsonType)) {
                object = new JSONArray(json);
            } else {
                throw new Exception("Json字符串格式不正确");
            }
            return XML.toString(object);
        } catch (Throwable e) {
            logger.error("Json字符串转换成XML字符串失败", e);
            return null;
        }
    }
}
