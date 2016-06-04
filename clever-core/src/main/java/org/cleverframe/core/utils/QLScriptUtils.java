package org.cleverframe.core.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.QLScript;
import org.cleverframe.core.qlscript.IQLScriptService;
import org.cleverframe.core.qlscript.QLScriptTemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * 获取脚本工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-29 10:29 <br/>
 */
public class QLScriptUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(QLScriptUtils.class);

    /**
     * FreeMarker容器
     */
    private static final Configuration CONFIGURATION;

    /**
     * 数据库脚本Service
     */
    private static final IQLScriptService QLSCRIPT_SERVICE;

    // 生产环境使用
    static {
        try {
            CONFIGURATION = new Configuration(Configuration.VERSION_2_3_23);
            CONFIGURATION.setLocale(Locale.SIMPLIFIED_CHINESE);
            // FreeMarker 模版数据null值处理，不抛异常
            // CONFIGURATION.setClassicCompatible(true);

            QLScriptTemplateLoader qlScriptTemplateLoader = SpringContextHolder.getBean(CoreBeanNames.QLScriptTemplateLoader);
            assert qlScriptTemplateLoader != null;
            qlScriptTemplateLoader.setLocale(CONFIGURATION.getLocale().toString());
            CONFIGURATION.setTemplateLoader(qlScriptTemplateLoader);

            QLSCRIPT_SERVICE = SpringContextHolder.getBean(CoreBeanNames.EhCacheQLScriptService);
        } catch (Throwable e) {
            logger.error("### QLScriptUtils类初始化失败", e);
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 获取QLScript对象<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name 脚本名称，使用包名称+类名+方法名
     * @return 若脚本不存在返回null
     */
    public static QLScript getScript(String name) {
        QLScript qLScript = QLSCRIPT_SERVICE.getQLScriptByName(name);
        if (qLScript == null) {
            return null;
        }
        return qLScript;
    }

    /**
     * 获取SQL脚本<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name 脚本名称，使用包名称+类名+方法名
     * @return 若脚本不存在，抛出异常
     */
    public static String getSQLScript(String name) {
        QLScript qLScript = QLSCRIPT_SERVICE.getQLScriptByName(name);
        if (qLScript == null || !QLScript.TYPE_SQL.equals(qLScript.getScriptType())) {
            throw new RuntimeException("脚本[" + name + "]不存在");
        }
        return qLScript.getScript();
    }

    /**
     * 获取HQL脚本<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name 脚本名称，使用包名称+类名+方法名
     * @return 若脚本不存在，抛出异常
     */
    public static String getHQLScript(String name) {
        QLScript qLScript = QLSCRIPT_SERVICE.getQLScriptByName(name);
        if (qLScript == null || !QLScript.TYPE_HQL.equals(qLScript.getScriptType())) {
            throw new RuntimeException("脚本[" + name + "]不存在");
        }
        return qLScript.getScript();
    }

    /**
     * 根据脚本名称和模板数据组装SQL脚本<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name      脚本名称，使用包名称+类名+方法名
     * @param dataModel 设置用于组装SQL的数据(模板数据)
     * @return 返回根据数据组装好的SQL脚本
     */
    public static String getSQLScript(String name, Map<String, Object> dataModel) {
        StringWriter sql = new StringWriter();
        try {
            Template template = CONFIGURATION.getTemplate(name);
            template.process(dataModel, sql);
        } catch (Throwable e) {
            logger.error("根据脚本名称和模板数据组装SQL脚本失败", e);
        }
        return sql.toString();
    }

    /**
     * 根据脚本名称和模板数据组装HQL脚本<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name      脚本名称，使用包名称+类名+方法名
     * @param dataModel 设置用于组装HQL的数据(模板数据)
     * @return 返回根据数据组装好的HQL脚本
     */
    public static String getHQLScript(String name, Map<String, Object> dataModel) {
        return getSQLScript(name, dataModel);
    }

    /**
     * 获取数据库脚本，并刷新QLScript缓存和FreeMarker缓存<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name 脚本名称，使用包名称+类名+方法名
     * @return 刷新缓存成功返回新的QLScript，否则返回null
     */
    public static QLScript refreshQLScript(String name) {
        QLScript qLScript = QLSCRIPT_SERVICE.refreshQLScript(name);
        try {
            CONFIGURATION.removeTemplateFromCache(name);
        } catch (Throwable e) {
            logger.error("removeTemplateFromCache异常", e);
        }
        return qLScript;
    }

    /**
     * 根据脚本名称，移除FreeMarker缓存<br/>
     *
     * @param name 脚本名称，使用包名称+类名+方法名
     */
    public static void removeTemplateCache(String name) {
        try {
            CONFIGURATION.removeTemplateFromCache(name);
        } catch (Throwable e) {
            logger.error("根据脚本名称，移除FreeMarker缓存异常", e);
        }
    }
}
