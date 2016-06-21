package org.cleverframe.generator.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.format.*;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 17:39 <br/>
 */
@Service(GeneratorBeanNames.CodeFormatService)
public class CodeFormatService extends BaseService {
    /**
     * 格式化HTML
     *
     * @param html        HTML字符串
     * @param ajaxMessage 请求响应对象
     * @return 失败返回原html
     */
    public String htmlFormat(String html, AjaxMessage ajaxMessage) {
        String result;
        result = HtmlFormatUtils.htmlFormatByJsoup(html);
        if (StringUtils.isBlank(result)) {
            result = html;
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("格式化失败");
        }
        return result;
    }

    /**
     * 格式化Java代码
     *
     * @param javaCode    Java代码
     * @param ajaxMessage 请求响应对象
     * @return 失败返回原javaCode
     */
    public String javaFormat(String javaCode, AjaxMessage ajaxMessage) {
        String result;
        result = JavaFormatUtils.javaFormatByJDT(javaCode);
        if (StringUtils.isBlank(result)) {
            result = javaCode;
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("格式化失败");
        }
        return result;
    }

    /**
     * 格式化json
     *
     * @param json        json字符串
     * @param ajaxMessage 请求响应对象
     * @return 失败返回原json字符串
     */
    public String jsonFormat(String json, AjaxMessage ajaxMessage) {
        String result;
        result = JsonFormatUtils.jsonFormatByGson(json);
        if (StringUtils.isBlank(result)) {
            result = json;
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("格式化失败");
        }
        return result;
    }

    /**
     * 格式化Sql
     *
     * @param sql         sql字符串
     * @param ajaxMessage 请求响应对象
     * @return 失败返回原sql字符串
     */
    public String sqlFormat(String sql, AjaxMessage ajaxMessage) {
        String result;
        result = SqlFormatUtils.sqlFormatByHibernate(sql);
        if (StringUtils.isBlank(result)) {
            result = sql;
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("格式化失败");
        }
        return result;
    }

    /**
     * 格式化xml
     *
     * @param xml         xml字符串
     * @param ajaxMessage 请求响应对象
     * @return 失败返回原xml字符串
     */
    public String xmlFormat(String xml, AjaxMessage ajaxMessage) {
        String result;
        result = XmlFormatUtils.xmlFormatByDom4j(xml, null);
        if (StringUtils.isBlank(result)) {
            result = xml;
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("格式化失败");
        }
        return result;
    }
}
