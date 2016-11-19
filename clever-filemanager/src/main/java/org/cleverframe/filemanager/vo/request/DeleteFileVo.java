package org.cleverframe.filemanager.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/19 13:24 <br/>
 */
public class DeleteFileVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID不能为空
     */
    @NotBlank(message = "文件ID不能为空")
    private String uuid;

    /**
     * 删除类型 (如果值为true表示：只删除当前文件引用；值为false表示：直接从硬盘中删除服务器端文件)
     */
    @NotNull(message = "删除类型不能为空")
    private Boolean lazy;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }
}
