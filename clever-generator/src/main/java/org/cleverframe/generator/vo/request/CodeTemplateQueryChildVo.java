package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-18 15:02 <br/>
 */
public class CodeTemplateQueryChildVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 查询的全路径
     */
    @NotBlank(message = "节点路径不能为空")
    private String fullPath;

    /**
     * 排除的子节点全路径
     */
    private String excludePath;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }
}
