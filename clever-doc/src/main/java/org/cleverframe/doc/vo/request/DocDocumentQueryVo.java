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

    /**
     * 查询的全路径
     */
    private String fullPath;

    /**
     * 排除的子节点全路径
     */
    private String excludePath;

    /**
     * 是否有Root节点
     */
    private boolean hasRoot;

    /**
     * Root节点名称
     */
    private String rootName;

    /**
     * 节点是否打开
     */
    private boolean open;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }

    public boolean isHasRoot() {
        return hasRoot;
    }

    public void setHasRoot(boolean hasRoot) {
        this.hasRoot = hasRoot;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
