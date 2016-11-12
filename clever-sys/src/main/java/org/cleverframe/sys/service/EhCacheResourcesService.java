package org.cleverframe.sys.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.configuration.BaseConfigNames;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.ehcache.EhCacheNames;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.ResourcesDao;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.vo.response.ResourcesTreeNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/21 17:40 <br/>
 */
@DependsOn({SpringBeanNames.CacheManager, SpringBeanNames.SpringContextHolder})
@Service(SysBeanNames.EhCacheResourcesService)
public class EhCacheResourcesService extends BaseService implements IUserPermissionsService {

    public static final String STATIC_PATH = "staticPath";
    public static final String MVC_PATH = "mvcPath";
    public static final String MODULES_PATH = "modulesPath";
    public static final String DOC_PATH = "docPath";

    @Autowired
    @Qualifier(SysBeanNames.ResourcesDao)
    private ResourcesDao resourcesDao;

    /**
     * 静态资源基路径：static
     */
    private String staticPath;

    /**
     * MVC框架的请求映射基路径：mvc
     */
    private String mvcPath;

    /**
     * modules
     */
    private String modulesPath;

    /**
     * 系统文档基路径：doc
     */
    private String docPath;

    /**
     * 系统配置缓存<br/>
     * <b>注意：不缓存软删除了的数据</b>
     */
    private Cache resourcesCache = EhCacheUtils.createCache(EhCacheNames.ResourcesCache);

    @SuppressWarnings("Duplicates")
    @PostConstruct
    private void init() {
        IConfig config = SpringContextHolder.getBean(SpringBeanNames.Config);
        if (config == null) {
            throw new RuntimeException("### IConfig对象注入失败");
        }
        staticPath = config.getConfig(BaseConfigNames.STATIC_PATH);
        mvcPath = config.getConfig(BaseConfigNames.MVC_PATH);
        modulesPath = config.getConfig(BaseConfigNames.MODULES_PATH);
        docPath = config.getConfig(BaseConfigNames.DOC_PATH);
    }

    /**
     * 字符串变量替换，如： 123${name}456 - [name='qwe'] => 123qwe456
     *
     * @param str           原字符串
     * @param variableName  变量名称
     * @param variableValue 变量值
     * @return 替换后的字符串
     */
    private String replaceVariable(String str, String variableName, String variableValue) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        str = str.replace("${" + variableName + "}", variableValue);
        return str;
    }

    @Override
    public String getResourcesKey(String resourcesUrl) {
        if (StringUtils.isBlank(resourcesUrl)) {
            return "";
        }
        String result = replaceVariable(resourcesUrl, STATIC_PATH, staticPath);
        result = replaceVariable(result, MVC_PATH, mvcPath);
        result = replaceVariable(result, MODULES_PATH, modulesPath);
        result = replaceVariable(result, DOC_PATH, docPath);
        return result;
    }

    @Override
    public List<Resources> reloadResources() {
        resourcesCache.removeAll();
        List<Resources> resourcesList = resourcesDao.findAllResources();
        for (Resources resources : resourcesList) {
            Element element = new Element(getResourcesKey(resources.getResourcesUrl()), resources);
            resourcesCache.put(element);
        }
        return resourcesList;
    }

    @Override
    public Resources getResources(String resourcesKey) {
        Element element = resourcesCache.get(resourcesKey);
        return element == null ? null : (Resources) element.getObjectValue();
    }

    /**
     * 保存资源信息
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addResources(Resources resources) {
        resourcesDao.getHibernateDao().save(resources);
        Element element = new Element(getResourcesKey(resources.getResourcesUrl()), resources);
        resourcesCache.put(element);
        return true;
    }

    /**
     * 保存资源信息
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    @Override
    public boolean updateResources(Resources resources) {
        Resources oldResources1 = resourcesDao.getHibernateDao().get(resources.getId());
        resourcesCache.remove(getResourcesKey(oldResources1.getResourcesUrl()));
        resources = resourcesDao.getHibernateDao().update(resources, false, true);
        Element element = new Element(getResourcesKey(resources.getResourcesUrl()), resources);
        resourcesCache.put(element);
        return true;
    }

    /**
     * 删除资源信息
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteResources(Serializable resourcesId) {
        // TODO 验证当前资源有没有被其他资源所依赖，若有则不能删除
        Resources oldResources1 = resourcesDao.getHibernateDao().get(resourcesId);
        resourcesCache.remove(getResourcesKey(oldResources1.getResourcesUrl()));
        return resourcesDao.getHibernateDao().deleteById(resourcesId) >= 1;
    }

    /**
     * 分页查询数据
     *
     * @param title         资源标题
     * @param resourcesUrl  资源URL地址
     * @param permission    资源访问所需要的权限标识字符串
     * @param resourcesType 包含的资源类型（1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源） 如:('2', '3')
     */
    public Page<Resources> findByPage(Page<Resources> page, String title, String resourcesUrl, String permission, String resourcesType) {
        return resourcesDao.findByPage(page, title, resourcesUrl, permission, resourcesType);
    }

    /**
     * 查询一个页面资源的所有依赖资源
     *
     * @param resourcesId 页面资源ID
     */
    public List<Resources> findDependenceResources(Serializable resourcesId) {
        return resourcesDao.findDependenceResources(resourcesId);
    }

    /**
     * 为页面资源增加一个依赖资源
     *
     * @param resourcesId           资源ID
     * @param dependenceResourcesId 依赖的资源ID
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean addDependenceResources(Serializable resourcesId, Serializable dependenceResourcesId) {
        // TODO 验证 resourcesId dependenceResourcesId 存在
        return resourcesDao.addDependenceResources(resourcesId, dependenceResourcesId);
    }

    /**
     * 为页面资源删除一个依赖资源
     *
     * @param resourcesId           资源ID
     * @param dependenceResourcesId 依赖的资源ID
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean deleteDependenceResources(Serializable resourcesId, Serializable dependenceResourcesId) {
        return resourcesDao.deleteDependenceResources(resourcesId, dependenceResourcesId);
    }

    /**
     * 查询资源依赖树(查询系统所有资源:只分两级，页面资源和后台资源)
     */
    @SuppressWarnings("Convert2streamapi")
    public List<ResourcesTreeNodeVo> findResourcesTree() {
        List<ResourcesTreeNodeVo> tree = new ArrayList<>();
        List<Resources> resourcesList = resourcesDao.findAllResources();
        for (Resources resources : resourcesList) {
            if (Resources.WEB_PAGE.equals(resources.getResourcesType())) {
                ResourcesTreeNodeVo resourcesTreeNodeVo = BeanMapper.mapper(resources, ResourcesTreeNodeVo.class);
                resourcesTreeNodeVo.setParentId(-1L);
                tree.add(resourcesTreeNodeVo);
            }
        }
        List<Map<String, Object>> relationList = resourcesDao.findAllResourcesRelation();
        // 构建树
        for (ResourcesTreeNodeVo resourcesTreeNodeVo : tree) {
            List<Object> dependenceResourcesIdList = new ArrayList<>();
            for (Map<String, Object> map : relationList) {
                if (resourcesTreeNodeVo.getId() != null
                        && map.get("resources_id") != null
                        && resourcesTreeNodeVo.getId().toString().equals(map.get("resources_id").toString())) {
                    dependenceResourcesIdList.add(map.get("dependence_resources_id"));
                }
            }
            // 增加子节点
            for (Object object : dependenceResourcesIdList) {
                for (Resources resources : resourcesList) {
                    if (object != null && resources.getId() != null && object.toString().equals(resources.getId().toString())) {
                        // 增加依赖资源信息
                        if (resourcesTreeNodeVo.getChildren() == null) {
                            resourcesTreeNodeVo.setChildren(new ArrayList<>());
                        }
                        ResourcesTreeNodeVo children = BeanMapper.mapper(resources, ResourcesTreeNodeVo.class);
                        children.setParentId(resourcesTreeNodeVo.getId());
                        resourcesTreeNodeVo.getChildren().add(children);
                    }
                }
            }
        }
        return tree;
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getStaticPath() {
        return staticPath;
    }

    public String getMvcPath() {
        return mvcPath;
    }

    public String getModulesPath() {
        return modulesPath;
    }

    public String getDocPath() {
        return docPath;
    }
}
