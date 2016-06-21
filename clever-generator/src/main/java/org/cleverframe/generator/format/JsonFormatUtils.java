package org.cleverframe.generator.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json格式化，使用Gson、Fastjson实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 0:07 <br/>
 */
public class JsonFormatUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(JsonFormatUtils.class);

    /**
     * 使用Gson格式化json字符串
     *
     * @param json json字符串
     * @return 失败返回 ""
     */
    public static String jsonFormatByGson(String json) {
        String result = "";
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(json);
            result = gson.toJson(jsonElement);
        } catch (Throwable e) {
            logger.error("Json格式化失败[Gson]", e);
        }
        return result;
    }

    /**
     * 使用FastJson格式化json字符串
     *
     * @param json json字符串
     * @return 失败返回 ""
     */
    public static String jsonFormatByFastJson(String json) {
        String result = "";
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
        } catch (Throwable e) {
            logger.error("Json格式化失败[FastJson]", e);
        }
        return result;
    }
}
