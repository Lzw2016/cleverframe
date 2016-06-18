package org.cleverframe.core.freemarker;

import freemarker.cache.TemplateLoader;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.QLScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * 实现freemarker的模版加载器，从数据库加载"数据库脚本"模版<br/>
 * 实现参考：{@link freemarker.cache.StringTemplateLoader}
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-29 10:20 <br/>
 *
 * @see freemarker.cache.StringTemplateLoader
 * @see freemarker.cache.FileTemplateLoader
 */
@Component(CoreBeanNames.QLScriptTemplateLoader)
public class QLScriptTemplateLoader implements TemplateLoader {
    /**
     * 日志对象
     */
    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(QLScriptTemplateLoader.class);

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheQLScriptService)
    private IQLScriptService qLScriptService;

    /**
     * FreeMarker配置的区域信息，如 ：“zh_CN”、“en_US”
     */
    private String locale;

    /**
     * 根据名称返回指定的模版资源
     */
    @Override
    public Object findTemplateSource(String name) throws IOException {
        name = StringUtils.replace(name, "_" + locale, "");
        QLScript qLScript = qLScriptService.getQLScriptByName(name);
        if (qLScript == null) {
            RuntimeException exception = new RuntimeException("脚本不存在，脚本名称：" + name);
            logger.error(exception.getMessage(), exception);
        }
        return qLScript;
    }

    /**
     * 返回模版资源最后一次修改的时间
     */
    @Override
    public long getLastModified(Object templateSource) {
        QLScript qLScript = (QLScript) templateSource;
        if (qLScript.getUpdateDate() != null) {
            return qLScript.getUpdateDate().getTime();
        } else {
            return qLScript.getCreateDate().getTime();
        }
    }

    /**
     * 返回读取模版资源的 Reader
     */
    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        QLScript qLScript = (QLScript) templateSource;
        return new StringReader(qLScript.getScript());
    }

    /**
     * 关闭模板源
     */
    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
