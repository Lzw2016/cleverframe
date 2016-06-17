package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 删除(Config)的请求参数对象<br/>
 *
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 14:45 <br/>
 */
public class ConfigDelVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     */
    @NotBlank(message = "配置键不能为空")
    @Length(min = 1, max = 100, message = "配置键长度不能超过100个字符")
    private String configKey;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}
