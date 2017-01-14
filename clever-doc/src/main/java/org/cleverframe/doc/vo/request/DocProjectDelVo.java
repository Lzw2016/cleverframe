package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/13 21:25 <br/>
 */
public class DocProjectDelVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "数据的id不能为空")
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
