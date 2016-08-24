package org.cleverframe.sys.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
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

}