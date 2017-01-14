package org.cleverframe.sys.dao;

import org.apache.commons.lang.math.NumberUtils;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Menu;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DAO，对应表sys_menu(菜单表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:47:29 <br/>
 */
@Repository(SysBeanNames.MenuDao)
public class MenuDao extends BaseDao<Menu> {

    /**
     * 分页查询菜单数据
     *
     * @param menuType 菜单类型
     * @param name     菜单名称
     * @param openMode 菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)
     */
    public Page<Menu> findByPage(Page<Menu> page, String menuType, String name, Character openMode) {
        Parameter param = new Parameter();
        param.put("menuType", menuType);
        param.put("name", "%" + name + "%");
        param.put("openMode", openMode);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.MenuDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 查询所有菜单类型
     */
    @SuppressWarnings({"unchecked", "UnnecessaryLocalVariable"})
    public List<Map<String, Object>> findAllMenuType() {
        Parameter param = new Parameter();
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.MenuDao.findAllMenuType");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = sqlQuery.list();
        return list;
    }

    /**
     * 根据类型查询菜单
     *
     * @param menuType 菜单类型
     */
    public List<Menu> findMenuByType(String menuType) {
        Parameter param = new Parameter();
        param.put("menuType", menuType);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.MenuDao.findMenuByType");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 查询菜单的直接子菜单个数(包含软删除的)
     */
    public long findChildMenuCount(Serializable id) {
        Parameter param = new Parameter();
        param.put("id", id);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.MenuDao.findChildMenuCount");
        Query query = hibernateDao.createSqlQuery(sql, param);
        Object count = query.uniqueResult();
        return NumberUtils.toLong(count.toString(), 0L);
    }
}