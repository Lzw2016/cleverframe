package org.cleverframe.common.freemarker;

import freemarker.template.Configuration;
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

    public Configuration getConfiguration() {
        return configuration;
    }
}
