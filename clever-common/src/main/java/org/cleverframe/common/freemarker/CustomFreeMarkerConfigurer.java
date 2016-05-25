package org.cleverframe.common.freemarker;

import freemarker.template.Configuration;
import org.cleverframe.common.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

/**
 * 自定义的FreeMarkerConfigurer，实现Spring与FreeMarker整合而不使用JSP的功能<br/>
 * 此类需要通过Spring实例化注入<br/>
 * 参考类：{@link org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer}<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-25 9:09 <br/>
 */
public class CustomFreeMarkerConfigurer extends FreeMarkerConfigurationFactory implements InitializingBean, ResourceLoaderAware {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(CustomFreeMarkerConfigurer.class);

    /**
     * FreeMarker容器
     */
    private Configuration configuration;

    /**
     * Spring实例化该Bean之后回调的方法<br/>
     * 实例化Configuration属性<br/>
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.configuration == null) {
            this.configuration = createConfiguration();
        }
    }

    /**
     * @return 返回FreeMarker容器
     */
    public Configuration getConfiguration() {
        if (this.configuration == null) {
            try {
                this.configuration = createConfiguration();
            } catch (Throwable e) {
                logger.error("CustomFreeMarkerConfigurer创建getConfiguration失败", e);
                throw ExceptionUtils.unchecked(e);
            }
        }
        return this.configuration;
    }
}
