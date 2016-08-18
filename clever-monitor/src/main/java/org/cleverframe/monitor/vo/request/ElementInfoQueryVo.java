package org.cleverframe.monitor.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-18 16:41 <br/>
 */
public class ElementInfoQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "缓存名称不能为空")
    private String cacheName;

    @NotBlank(message = "缓存键不能为空")
    private String key;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
