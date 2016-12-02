package org.cleverframe.core.initialize;

import org.cleverframe.common.configuration.CustomPropertyPlaceholderConfigurer;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.initialize.IHandle;
import org.cleverframe.common.spring.SpringBeanNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 把配置信息保存到数据库里
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 0:29 <br/>
 */
@Service(SpringBeanNames.ConfigInitHandle)
public class ConfigInitHandle implements IHandle {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigInitHandle.class);

    @Autowired
    @Qualifier(SpringBeanNames.Config)
    private IConfig config;

    @Transactional(readOnly = false)
    @Override
    public int initialize(ContextRefreshedEvent event) {
        int addCfgCount = 0;
        // 必须清除缓存数据，防止数据库与缓存中的数据不一样
        config.clearAllCache();
        Map<String, String> dBAllCfg = config.getAllConfig();
        Map<String, String> propertiesCfg = CustomPropertyPlaceholderConfigurer.PropertiesMap;
        Map<String, String> addCfgMap = new HashMap<>();
        // 找出与数据库里不同的配置信息
        for (Map.Entry<String, String> entry : propertiesCfg.entrySet()) {
            String value = dBAllCfg.get(entry.getKey());
            if (value == null || !value.equals(entry.getValue())) {
                addCfgMap.put(entry.getKey(), entry.getValue());
            }
        }

        // 保存或更新不同的配置
        StringBuilder strTmp = new StringBuilder();
        strTmp.append("\r\n");
        strTmp.append("#=======================================================================================================================#\r\n");
        strTmp.append("# 新增的配置如下:\r\n");
        for (Map.Entry<String, String> entry : addCfgMap.entrySet()) {
            boolean flag = config.updateOrAddConfig(entry.getKey(), entry.getValue());
            if (flag) {
                strTmp.append("#\t ").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\r\n");
                addCfgCount++;
            }
        }
        strTmp.append("#=======================================================================================================================#");
        if (logger.isInfoEnabled() && addCfgCount > 0) {
            logger.info(strTmp.toString());
        }
        config.reLoadAll();
        return addCfgCount;
    }
}
