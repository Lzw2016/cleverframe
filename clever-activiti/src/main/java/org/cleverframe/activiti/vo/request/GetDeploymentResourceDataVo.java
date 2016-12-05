package org.cleverframe.activiti.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/5 20:49 <br/>
 */
public class GetDeploymentResourceDataVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 流程部署ID
     */
    @NotBlank(message = "流程部署ID不能为空")
    private String deploymentId;

    /**
     * 流程源数据ID
     */
    @NotBlank(message = "流程源数据ID不能为空")
    private String resourceId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
