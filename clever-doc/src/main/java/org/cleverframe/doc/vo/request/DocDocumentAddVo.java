package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/13 21:48 <br/>
 */
public class DocDocumentAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 备注
     */
    @Length(max = 255, message = "备注信息值长度不能超过255个字符")
    private String remarks;

    /**
     * 文档项目ID-关联doc_project(文档项目表)
     */
    @NotNull(message = "文档项目ID不能乐观为空")
    private Long projectId;

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    @NotNull(message = "上级文档ID不能为空")
    private Long parentId;

    /**
     * 文档或者章节的标题
     */
    @NotBlank(message = "文档标题不能为空")
    @Length(max = 100, message = "文档标题长度不能超过100个字符")
    private String title;

    /**
     * 文档内容
     */
    @Length(max = 2000000, message = "文档内容长度不能超过2000000个字符")
    private String content;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
