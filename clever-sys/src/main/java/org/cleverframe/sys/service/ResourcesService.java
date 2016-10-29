package org.cleverframe.sys.service;

import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.ResourcesDao;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.vo.response.ResourcesTreeNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/21 17:40 <br/>
 */
@Service(SysBeanNames.ResourcesService)
public class ResourcesService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.ResourcesDao)
    private ResourcesDao resourcesDao;

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
     * 保存资源信息
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean addResources(Resources resources) {
        resourcesDao.getHibernateDao().save(resources);
        return true;
    }

    /**
     * 保存资源信息
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean updateResources(Resources resources) {
        resourcesDao.getHibernateDao().update(resources, false, true);
        return true;
    }

    /**
     * 删除资源信息
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean deleteResources(Serializable resourcesId) {
        // TODO 验证当前资源有没有被其他资源所依赖，若有则不能删除
        return resourcesDao.getHibernateDao().deleteById(resourcesId) >= 1;
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
}
