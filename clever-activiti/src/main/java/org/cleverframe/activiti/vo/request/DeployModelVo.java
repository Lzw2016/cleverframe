package org.cleverframe.activiti.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/6 19:36 <br/>
 */
public class DeployModelVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 模型ID
     */
    @NotBlank(message = "模型ID不能为空")
    private String modelId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
