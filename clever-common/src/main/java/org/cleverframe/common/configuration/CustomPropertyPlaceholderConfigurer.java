package org.cleverframe.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义的属性文件配置处理器,处理*.properties文件里的数据<br/>
 * 1.解密.properties文件里敏感数据<br/>
 * 2.读取并缓存.properties文件的配置数据<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-14 0:20 <br/>
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(CustomPropertyPlaceholderConfigurer.class);

    /**
     * 系统属性配置文件的配置<br/>
     */
    public final static Map<String, String> PropertiesMap = new HashMap<>();

    /**
     * 处理属性数据，如：保存在系统缓存中
     *
     * @param beanFactoryToProcess BeanFactory
     * @param props                配置属性数据
     * @throws BeansException
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
    }

    /**
     * 转换处理属性值
     *
     * @param props 配置属性数据
     */
    @Override
    protected void convertProperties(Properties props) {
        super.convertProperties(props);
    }

    /**
     * 转换处理属性值
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @return 转换后的值
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        PropertiesMap.put(propertyName, propertyValue);
        logger.debug("### propertyName={} | propertyValue={}", propertyName, propertyValue);
        return super.convertProperty(propertyName, propertyValue);
    }

    /**
     * 转换处理属性值
     *
     * @param originalValue 属性值
     * @return 转换后的值
     */
    @Override
    protected String convertPropertyValue(String originalValue) {
        return super.convertPropertyValue(originalValue);
    }
}
