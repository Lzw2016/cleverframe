package org.cleverframe.doc.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 实体类，对应表doc_project(文档项目表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-12 22:07:44 <br/>
 */
@Entity
@Table(name = "doc_project")
@DynamicInsert
@DynamicUpdate
public class DocProject extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文档项目名称
     */
    private String name;

    /**
     * 文档介绍和说明
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String readme;

    /**
     * 文档目录
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String summary;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 文档项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 文档项目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 文档介绍和说明
     */
    public String getReadme() {
        return readme;
    }

    /**
     * 设置 文档介绍和说明
     */
    public void setReadme(String readme) {
        this.readme = readme;
    }

    /**
     * 获取 文档目录
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置 文档目录
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

}