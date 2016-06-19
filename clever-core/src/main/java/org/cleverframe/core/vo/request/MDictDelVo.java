package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 14:38 <br/>
 */
public class MDictDelVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 多级字典路径
     */
    @NotBlank(message = "多级字典路径不能为空")
    private String fullPath;

    /*--------------------------------------------------------------
    *          getter、setter
    * -------------------------------------------------------------*/

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
