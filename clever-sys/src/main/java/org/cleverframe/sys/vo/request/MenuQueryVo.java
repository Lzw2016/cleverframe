package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/5 12:34 <br/>
 */
public class MenuQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)
     */
    private Character openMode;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Character openMode) {
        this.openMode = openMode;
    }
}
