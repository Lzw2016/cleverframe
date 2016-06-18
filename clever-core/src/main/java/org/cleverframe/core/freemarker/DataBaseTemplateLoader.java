package org.cleverframe.core.freemarker;

import freemarker.cache.TemplateLoader;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * 数据库模版加载器实现,从数据库中加载模版<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 12:27 <br/>
 */
public class DataBaseTemplateLoader implements TemplateLoader {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(DataBaseTemplateLoader.class);

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheTemplateService)
    private ITemplateService templateService;

    /**
     * 根据名称返回指定的模版资源
     */
    @Override
    public Object findTemplateSource(String name) throws IOException {
        logger.debug("### DataBaseTemplateLoader.findTemplateSource 获取模版,name = {}", name);
        Template template = templateService.getTemplateByName(name);
        if (template == null) {
            logger.info("### DataBaseTemplateLoader.findTemplateSource 模版不存在，模版名称：{}", name);
        }
        return template;
    }

    /**
     * 返回模版资源最后一次修改的时间
     */
    @Override
    public long getLastModified(Object templateSource) {
        Template template = (Template) templateSource;
        if (template.getUpdateDate() != null) {
            return template.getUpdateDate().getTime();
        } else {
            return template.getCreateDate().getTime();
        }
    }

    /**
     * 返回读取模版资源的 Reader
     */
    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        Template template = (Template) templateSource;
        return new StringReader(template.getContent());
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }
}
