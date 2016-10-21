package org.cleverframe.sys.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.ResourcesDao;
import org.cleverframe.sys.entity.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
     * @param resourcesType 资源类型（1：URL资源；2：UI资源）
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
    public boolean deleteResources(Serializable id) {
        return resourcesDao.getHibernateDao().deleteById(id) >= 1;
    }
}
