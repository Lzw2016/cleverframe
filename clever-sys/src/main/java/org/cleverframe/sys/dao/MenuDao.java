package org.cleverframe.sys.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Menu;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表sys_menu(菜单表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:47:29 <br/>
 */
@Repository(SysBeanNames.MenuDao)
public class MenuDao extends BaseDao<Menu> {

}