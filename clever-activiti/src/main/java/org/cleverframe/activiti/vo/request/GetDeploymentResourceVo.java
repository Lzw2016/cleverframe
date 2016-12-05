package org.cleverframe.activiti.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/5 20:17 <br/>
 */
public class GetDeploymentResourceVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 流程部署ID
     */
    @NotBlank(message = "流程部署ID不能为空")
    private String deploymentId;

    /**
     * 流程源数据名称
     */
    @NotBlank(message = "流程源数据名称不能为空")
    private String resourceName;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
