package org.cleverframe.sys.service;


import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
}
