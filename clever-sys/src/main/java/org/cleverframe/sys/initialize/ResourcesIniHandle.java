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
@Service(SpringBeanNames.ResourcesIniHandle)
public class ResourcesIniHandle implements IHandle {

    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ResourcesIniHandle.class);

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
        // 获取系统中所有的Url请求地址 - urlList
        RequestMappingHandlerMapping handlerMapping = event.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> set = map.keySet();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo requestMappingInfo : set) {
            Set<String> urlArray = requestMappingInfo.getPatternsCondition().getPatterns();
            urlList.addAll(urlArray);
        }
        Collections.sort(urlList);
        // 新增系统中所有的资源 - addUrlList
        List<String> addUrlList = new ArrayList<>();
        for (String url : urlList) {
            String resourcesUrl = ehCacheResourcesService.getResourcesUrl(url);
            Resources resources = resourcesDao.getResources(resourcesUrl);
            if (resources == null) {
                Resources newResources = new Resources();
                newResources.setTitle("资源标题");
                newResources.setResourcesUrl(resourcesUrl);
                newResources.setPermission(IDCreateUtils.uuid());
                newResources.setResourcesType(Resources.WEB_PAGE);
                newResources.setNeedAuthorization(Resources.NO_NEED);
                newResources.setDescription("系统自动生成, 必须手动配置");
                resourcesDao.getHibernateDao().save(newResources);
                addUrlList.add(resourcesUrl);
            }
        }
        // 统计数据库中有而系统中没有的资源数据 - notExistUrlList
        List<Resources> resourcesList = ehCacheResourcesService.reloadResources();
        List<String> notExistUrlList = new ArrayList<>();
        for (Resources resources : resourcesList) {
            String resourcesUrl = resources.getResourcesUrl();
            String resourcesKey = ehCacheResourcesService.getResourcesKey(resourcesUrl);
            if (urlList.contains(resourcesKey)) {
                continue;
            }
            notExistUrlList.add(resourcesUrl);
        }
        // 打印相应的日志
        StringBuilder strTmp = new StringBuilder();
        strTmp.append("\r\n");
        strTmp.append("#=======================================================================================================================#\r\n");
        strTmp.append("# 新增的资源如下(").append(addUrlList.size()).append("条):\r\n");
        for (String url : addUrlList) {
            strTmp.append("#\t ").append(url).append("\r\n");
        }
        strTmp.append("# 未匹配到的数据库里的资源(").append(notExistUrlList.size()).append("条):\r\n");
        for (String url : notExistUrlList) {
            strTmp.append("#\t ").append(url).append("\r\n");
        }
        strTmp.append("#=======================================================================================================================#");
        if (logger.isInfoEnabled() && (addUrlList.size() > 0 || notExistUrlList.size() > 0)) {
            logger.info(strTmp.toString());
        }
        return addUrlList.size();
    }
}
