package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/22 2:27 <br/>
 */
public class DependenceResourcesAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @NotNull(message = "资源ID不能为空")
    private Long resourcesId;

    /**
     * 依赖的资源ID
     */
    @NotNull(message = "依赖的资源ID不能为空")
    private Long dependenceResourcesId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Long getDependenceResourcesId() {
        return dependenceResourcesId;
    }

    public void setDependenceResourcesId(Long dependenceResourcesId) {
        this.dependenceResourcesId = dependenceResourcesId;
    }
}
