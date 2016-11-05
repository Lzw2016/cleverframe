package org.cleverframe.sys.service;


import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.MenuDao;
import org.cleverframe.sys.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Service，对应表sys_menu(菜单表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:47:30 <br/>
 */
@Service(SysBeanNames.MenuService)
public class MenuService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.MenuDao)
    private MenuDao menuDao;

    /**
     * 分页查询菜单数据
     *
     * @param menuType 菜单类型
     * @param name     菜单名称
     * @param openMode 菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)
     */
    public Page<Menu> findByPage(Page<Menu> page, String menuType, String name, Character openMode) {
        return menuDao.findByPage(page, menuType, name, openMode);
    }

    /**
     * 查询所有菜单类型
     */
    public List<Map<String, Object>> findAllMenuType() {
        return menuDao.findAllMenuType();
    }

    /**
     * 根据类型查询菜单
     */
    public List<Menu> findMenuByType(String menuType) {
        return menuDao.findMenuByType(menuType);
    }

    /**
     * 保存菜单数据
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = true)
    public boolean saveMenu(Menu menu) {
        menuDao.getHibernateDao().save(menu);
        return true;
    }

    /**
     * 更新菜单数据
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = true)
    public boolean updateMenu(Menu menu) {
        menuDao.getHibernateDao().update(menu, false, true);
        return true;
    }

    /**
     * 删除菜单数据(软删除)
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = true)
    public boolean deleteMenu(Serializable id) {
        int count = menuDao.getHibernateDao().deleteByIdForSoft(id);
        return count == 1;
    }
}
