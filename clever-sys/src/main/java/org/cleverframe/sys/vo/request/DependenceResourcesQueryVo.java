package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/22 2:14 <br/>
 */
public class DependenceResourcesQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @NotNull(message = "资源ID不能为空")
    private Long id;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
