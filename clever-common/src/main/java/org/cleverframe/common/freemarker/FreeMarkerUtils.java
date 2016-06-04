package org.cleverframe.common.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Locale;

/**
 * FreeMarker模板引擎工具类<br/>
 * <p/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-25 9:02 <br/>
 */
public class FreeMarkerUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FreeMarkerUtils.class);

    /**
     * 模版默认编码格式
     */
    public static final String defaultEncoding = "UTF-8";

    /**
     * FreeMarker容器
     */
    private final static Configuration CONFIGURATION;

    // 测试使用
//    static {
//        CONFIGURATION = new Configuration(Configuration.VERSION_2_3_23);
//        StringTemplateLoader stringLoader = new StringTemplateLoader();
//        CONFIGURATION.setTemplateLoader(stringLoader);
//        // FreeMarker 模版数据null值处理，不抛异常
//        CONFIGURATION.setClassicCompatible(true);
//    }

    // 生产环境使用
    static {
        Configuration configuration = null;
        CustomFreeMarkerConfigurer customFreeMarkerConfigurer = SpringContextHolder.getBean(SpringBeanNames.CustomFreeMarkerConfigurer);
        if (null != customFreeMarkerConfigurer) {
            configuration = customFreeMarkerConfigurer.getConfiguration();
        } else {
            RuntimeException e = new RuntimeException("未注入Bean：" + SpringBeanNames.CustomFreeMarkerConfigurer);
            logger.error(e.getMessage(), e);
        }
        CONFIGURATION = configuration;
        if (CONFIGURATION == null) {
            RuntimeException e = new RuntimeException("FreeMarkerUtils.CONFIGURATION 初始化失败");
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取模版字符串
     *
     * @param name          模版名称
     * @param locale        模版环境语言
     * @param encoding      模版编码格式
     * @param ignoreMissing true:找不到模版不抛异常
     * @return 返回模版字符串，获取失败返回null
     */
    public static String getTemplateStr(String name, Locale locale, String encoding, boolean ignoreMissing) {
        String templateStr = null;
        Template template = null;
        try {
            template = CONFIGURATION.getTemplate(name, locale, null, encoding, false, ignoreMissing);
        } catch (Throwable e) {
            logger.error("getTemplateStr-获取模版失败", e);
        }
        if (null != template) {
            templateStr = template.toString();
        }
        return templateStr;
    }

    /**
     * 获取模版字符串
     *
     * @param name     模版名称
     * @param encoding 模版编码格式
     * @return 返回模版字符串，获取失败返回null
     */
    public static String getTemplateStr(String name, String encoding) {
        return getTemplateStr(name, null, encoding, true);
    }

    /**
     * 获取模版字符串，默认使用UTF-8编码
     *
     * @param name 模版名称
     * @return 返回模版字符串，获取失败返回null
     */
    public static String getTemplateStr(String name) {
        return getTemplateStr(name, null, defaultEncoding, true);
    }

    /**
     * 根据模版和数据，生成处理后的字符串<br/>
     *
     * @param name                  模版名称
     * @param locale                模版环境语言
     * @param customLookupCondition customLookupCondition
     * @param encoding              模版编码格式
     * @param ignoreMissing         true:找不到模版不抛异常
     * @param dataModel             需要绑定的数据，可以是Map、Set、ModelBean...
     * @return 返回处理完之后的字符串，失败返回null
     */
    public static String templateBindData(String name, Locale locale, Object customLookupCondition, String encoding, boolean ignoreMissing, Object dataModel) {
        String result = null;
        try {
            Template template = CONFIGURATION.getTemplate(name, locale, customLookupCondition, encoding, true, ignoreMissing);
            if (null != template) {
                StringWriter stringWriter = new StringWriter();
                template.process(dataModel, stringWriter);
                stringWriter.flush();
                stringWriter.close();
                result = stringWriter.toString();
            }
        } catch (Throwable e) {
            logger.error("templateBindData-模版数据绑定失败", e);
        }
        return result;
    }

    /**
     * 根据模版和数据，生成处理后的字符串<br/>
     *
     * @param name      模版名称
     * @param encoding  模版编码格式
     * @param dataModel 需要绑定的数据，可以是Map、Set、ModelBean...
     * @return 返回处理完之后的字符串，失败返回null
     */
    public static String templateBindData(String name, String encoding, Object dataModel) {
        return templateBindData(name, null, null, encoding, true, dataModel);
    }

    /**
     * 根据模版和数据，生成处理后的字符串<br/>
     *
     * @param name      模版名称
     * @param dataModel 需要绑定的数据，可以是Map、Set、ModelBean...
     * @return 返回处理完之后的字符串，失败返回null
     */
    public static String templateBindData(String name, Object dataModel) {
        return templateBindData(name, null, null, defaultEncoding, true, dataModel);
    }

    /**
     * 使用临时的模版根据数据绑定，生成处理后的字符串<br/>
     * <b>注意：此方式不会对模版进行缓存，没有使用TemplateLoader</b>
     *
     * @param templateStr 模版字符串
     * @param dataModel   需要绑定的数据，可以是Map、Set、ModelBean...
     * @return 返回处理完之后的字符串，失败返回null
     */
    public static String templateBindDataByTmp(String templateStr, Object dataModel) {
        String result = null;
        try {
            Template template = new Template(null, templateStr, CONFIGURATION);
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            stringWriter.flush();
            stringWriter.close();
            result = stringWriter.toString();
        } catch (Throwable e) {
            logger.error("templateBindDataByTmp-模版数据绑定失败", e);
        }
        return result;
    }
}
