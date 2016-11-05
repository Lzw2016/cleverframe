package org.cleverframe.sys.service;


import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.MenuDao;
import org.cleverframe.sys.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    @Transactional(readOnly = false)
    public boolean saveMenu(AjaxMessage message, Menu menu) {
        menu.setFullPath("-");
        menuDao.getHibernateDao().save(menu);
        if (menu.getParentId() <= -1L) {
            menu.setParentId(-1L);
            menu.setFullPath(menu.getId() + "");
        } else {
            Menu parentMenu = menuDao.getHibernateDao().get(menu.getParentId());
            if (parentMenu == null || !Menu.DEL_FLAG_NORMAL.equals(parentMenu.getDelFlag())) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                message.setSuccess(false);
                message.setFailMessage("新增菜单失败-上级菜单不存在");
                return false;
            }
            menu.setFullPath(parentMenu.getFullPath() + Menu.FULL_PATH_SPLIT + menu.getId());
            if (!parentMenu.getMenuType().equals(menu.getMenuType())) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                message.setSuccess(false);
                message.setFailMessage("新增菜单失败-菜单类型与上级菜单必须一致");
                return false;
            }
            menu.setMenuType(parentMenu.getMenuType());
        }
        menuDao.getHibernateDao().update(menu);
        return true;
    }

    /**
     * 更新菜单数据
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean updateMenu(Menu menu) {
        menuDao.getHibernateDao().update(menu, false, true);
        return true;
    }

    /**
     * 删除菜单数据
     *
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean deleteMenu(AjaxMessage message, Serializable id) {
        long count = menuDao.findChildMenuCount(id);
        if (count > 0) {
            message.setSuccess(false);
            message.setFailMessage("删除菜单失败-只能删除无子菜单的菜单");
            return false;
        }
        count = menuDao.getHibernateDao().deleteById(id);
        if (count != 1L) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            message.setSuccess(false);
            message.setFailMessage("删除菜单失败-菜单不存在");
            return false;
        }
        return true;
    }
}
