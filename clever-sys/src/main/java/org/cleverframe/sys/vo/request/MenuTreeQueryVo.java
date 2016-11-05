package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/5 14:51 <br/>
 */
public class MenuTreeQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型
     */
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
}
