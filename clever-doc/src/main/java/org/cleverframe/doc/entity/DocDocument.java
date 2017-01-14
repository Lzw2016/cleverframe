package org.cleverframe.doc.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 实体类，对应表doc_document(文档表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-12 22:22:40 <br/>
 */
@Entity
@Table(name = "doc_document")
@DynamicInsert
@DynamicUpdate
public class DocDocument extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文档项目ID-关联doc_project(文档项目表)
     */
    private Long projectId;

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    private Long parentId;

    /**
     * 树结构的全路径用“-”隔开,包含自己的ID
     */
    private String fullPath;

    /**
     * 文档或者章节的标题
     */
    private String title;

    /**
     * 文档内容
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String content;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取 父级编号,根节点的父级编号是：-1
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置 父级编号,根节点的父级编号是：-1
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取 树结构的全路径用“-”隔开,包含自己的ID
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * 设置 树结构的全路径用“-”隔开,包含自己的ID
     */
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    /**
     * 获取 文档或者章节的标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置 文档或者章节的标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取 文档内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 文档内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}