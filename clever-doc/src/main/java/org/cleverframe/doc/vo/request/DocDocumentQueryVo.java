package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/14 23:24 <br/>
 */
public class DocDocumentQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 文档项目ID-关联doc_project(文档项目表)
     */
    @NotNull(message = "文档项目ID不能为空")
    private Long projectId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
