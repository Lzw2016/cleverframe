package org.cleverframe.core.freemarker;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;

/**
 * 数据库模版加载器实现,从数据库中加载模版<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 12:27 <br/>
 */
public class DataBaseTemplateLoader implements TemplateLoader {
    @Override
    public Object findTemplateSource(String name) throws IOException {
        return null;
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return null;
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }
}
