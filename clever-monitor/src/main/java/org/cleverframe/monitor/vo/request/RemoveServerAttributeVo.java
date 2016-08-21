package org.cleverframe.monitor.vo.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-20 14:43 <br/>
 */
public class RemoveServerAttributeVo {

    @NotBlank(message = "属性名称不能为空")
    private String attributeName;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
