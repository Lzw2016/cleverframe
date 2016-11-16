package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/16 22:52 <br/>
 */
public class KickOutUserVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * Shiro Session ID
     */
    @NotBlank(message = "Shiro Session ID不能为空")
    private String sessionId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

