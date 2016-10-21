package org.cleverframe.sys.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Resources;
import org.springframework.stereotype.Repository;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/21 17:08 <br/>
 */
@Repository(SysBeanNames.ResourcesDao)
public class ResourcesDao extends BaseDao<Resources> {

    /**
     * 分页查询数据
     *
     * @param title         资源标题
     * @param resourcesUrl  资源URL地址
     * @param permission    资源访问所需要的权限标识字符串
     * @param resourcesType 资源类型（1：URL资源；2：UI资源）
     */
    public Page<Resources> findByPage(Page<Resources> page, String title, String resourcesUrl, String permission, String resourcesType) {
        Parameter param = new Parameter();
        param.put("title", "%" + title + "%");
        param.put("resourcesUrl", "%" + resourcesUrl + "%");
        param.put("permission", permission);
        param.put("resourcesType", resourcesType);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }
}
