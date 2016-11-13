package org.cleverframe.sys.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Organization;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表sys_organization(组织机构表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:42:15 <br/>
 */
@Repository(SysBeanNames.OrganizationDao)
public class OrganizationDao extends BaseDao<Organization> {

    /**
     * 根据机构编码查询机构（不含软删除的数据）
     *
     * @param code 机构编码
     * @return 机构信息
     */
    public Organization getOrganizationByCode(String code) {
        Parameter param = new Parameter(Organization.DEL_FLAG_NORMAL);
        param.put("code", code);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.OrganizationDao.getOrganizationByCode");
        return hibernateDao.getBySql(sql, param);
    }
}