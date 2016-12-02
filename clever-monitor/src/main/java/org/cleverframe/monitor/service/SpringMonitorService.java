package org.cleverframe.monitor.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.BeanInfoVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-19 23:32 <br/>
 */
@Component(MonitorBeanNames.SpringMonitorService)
public class SpringMonitorService extends BaseService {

    /**
     * 根据Spring Bean对象 创建BeanInfoVo
     *
     * @param beanName Bean名称
     * @param bean     Bean对象
     * @return 返回创建的BeanInfoVo
     */
    private BeanInfoVo getBeanInfoVo(String beanName, Object bean) {
        BeanInfoVo beanInfoVo = new BeanInfoVo();
        beanInfoVo.setName(beanName);
        if (bean != null) {
            beanInfoVo.setClazz(bean.getClass().getCanonicalName());
            beanInfoVo.setJsonValue(bean.toString());
        }
        return beanInfoVo;
    }

    /**
     * 返回Spring Context容器中的Bean<br>
     *
     * @param beanName Bean名称
     * @return Bean信息集合
     */
    public List<BeanInfoVo> getSpringBeans(String beanName) {
        List<BeanInfoVo> beanInfoVoList = new ArrayList<>();
        // 只查询一个Spring Bean
        if (StringUtils.isNotBlank(beanName)) {
            Object object = SpringContextHolder.getBean(beanName);
            if (object != null) {
                beanInfoVoList.add(this.getBeanInfoVo(beanName, object));
            }
            return beanInfoVoList;
        }

        // 获取所有的Spring Bean
        String[] beanNames = SpringContextHolder.getApplicationContext().getBeanDefinitionNames();
        for (String name : beanNames) {
            Object object = SpringContextHolder.getBean(name);
            beanInfoVoList.add(this.getBeanInfoVo(name, object));
        }
        return beanInfoVoList;
    }

    /**
     * 返回Spring Web容器中的Bean<br>
     *
     * @param beanName Bean名称
     * @return Bean信息集合
     */
    public List<BeanInfoVo> getSpringWebBeans(String beanName) {
        List<BeanInfoVo> beanInfoVoList = new ArrayList<>();
        // 只查询一个Spring Bean
        if (StringUtils.isNotBlank(beanName)) {
            Object object = SpringContextHolder.getWebBean(beanName);
            if (object != null) {
                beanInfoVoList.add(this.getBeanInfoVo(beanName, object));
            }
            return beanInfoVoList;
        }

        // 获取所有的Spring Bean
        String[] beanNames = SpringContextHolder.getWebApplicationContext().getBeanDefinitionNames();
        for (String name : beanNames) {
            Object object = SpringContextHolder.getWebBean(name);
            beanInfoVoList.add(this.getBeanInfoVo(name, object));
        }
        return beanInfoVoList;
    }
}
