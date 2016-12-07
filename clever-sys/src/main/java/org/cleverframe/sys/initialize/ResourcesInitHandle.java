package org.cleverframe.sys.initialize;

import org.cleverframe.common.initialize.IHandle;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.utils.IDCreateUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.ResourcesDao;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.service.EhCacheResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/12 19:56 <br/>
 */
@Service(SpringBeanNames.ResourcesInitHandle)
public class ResourcesInitHandle implements IHandle {

    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ResourcesInitHandle.class);

    @Autowired
    @Qualifier(SysBeanNames.ResourcesDao)
    private ResourcesDao resourcesDao;

    @Autowired
    @Qualifier(SysBeanNames.EhCacheResourcesService)
    private EhCacheResourcesService ehCacheResourcesService;

    // 新增数据库里没有的资源
    @Transactional(readOnly = false)
    @Override
    public int initialize(ContextRefreshedEvent event) {
        // 获取系统中所有的资源,并排序 - allUrlList
        List<Resources> allUrlList = new ArrayList<>();
        RequestMappingHandlerMapping handlerMapping = event.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMap = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMap.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            String fullMethodName = handlerMethod.getBeanType().getName() + "#" + handlerMethod.getMethod().getName();
            Set<String> urlArray = requestMappingInfo.getPatternsCondition().getPatterns();
            for (String url : urlArray) {
                // 数据库里保存有 变量的 url路径
                String resourcesUrl = ehCacheResourcesService.getResourcesUrl(url);
                Resources newResources = new Resources();
                newResources.setTitle("资源标题");
                newResources.setResourcesUrl(resourcesUrl);
                newResources.setControllerMethod(fullMethodName);
                newResources.setPermission(IDCreateUtils.uuid());
                newResources.setResourcesType(Resources.WEB_PAGE);
                newResources.setNeedAuthorization(Resources.NO_NEED);
                newResources.setDescription("系统自动生成, 必须手动配置");
                allUrlList.add(newResources);
            }
        }
        Collections.sort(allUrlList);
        // 保存系统中有而数据库里没有的资源 - addUrlList
        List<Resources> addUrlList = new ArrayList<>();
        List<Resources> allResources = resourcesDao.findAllResources();
        Map<String, String> mapResources = new HashMap<>();
        for (Resources resources : allResources) {
            mapResources.put(resources.getControllerMethod(), resources.getResourcesUrl());
        }
        for (Resources resources : allUrlList) {
            String resourcesUrl = mapResources.get(resources.getControllerMethod());
            if (resourcesUrl == null) {
                resourcesDao.getHibernateDao().save(resources);
                addUrlList.add(resources);
            }
        }
        // 统计数据库中有而系统中没有的资源数据 - notExistUrlList
        List<String> notExistUrlList = new ArrayList<>();
        // 重新加载数据库里所有的资源信息到缓存中 - resourcesList
        List<Resources> resourcesList = ehCacheResourcesService.reloadResources();
        logger.info("### 已重新加载所有的资源信息到缓存中");
        mapResources.clear();
        for (Resources resources : allUrlList) {
            mapResources.put(resources.getControllerMethod(), resources.getResourcesUrl());
        }
        // 数据库里有而系统不存在的资源信息
        for (Resources resources : resourcesList) {
            String resourcesUrl = mapResources.get(resources.getControllerMethod());
            if (resourcesUrl == null) {
                notExistUrlList.add(resources.getResourcesUrl());
            }
        }
        // 打印相应的日志
        if (logger.isInfoEnabled()) {
            StringBuilder strTmp = new StringBuilder();
            strTmp.append("\r\n");
            strTmp.append("#=======================================================================================================================#\r\n");
            strTmp.append("# 新增的资源如下(").append(addUrlList.size()).append("条):\r\n");
            for (Resources resources : addUrlList) {
                strTmp.append("#\t ").append(resources.getResourcesUrl()).append("\r\n");
            }
            strTmp.append("# 未匹配到的数据库里的资源(").append(notExistUrlList.size()).append("条):\r\n");
            for (String url : notExistUrlList) {
                strTmp.append("#\t ").append(url).append("\r\n");
            }
            strTmp.append("#=======================================================================================================================#");
            if (logger.isInfoEnabled() && (addUrlList.size() > 0 || notExistUrlList.size() > 0)) {
                logger.info(strTmp.toString());
            }
        }
        return addUrlList.size();
    }
}
