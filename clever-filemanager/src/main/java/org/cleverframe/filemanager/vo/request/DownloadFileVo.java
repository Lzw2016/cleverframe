package org.cleverframe.filemanager.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/19 12:26 <br/>
 */
public class DownloadFileVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID不能为空
     */
    @NotBlank(message = "文件ID不能为空")
    private String uuid;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
