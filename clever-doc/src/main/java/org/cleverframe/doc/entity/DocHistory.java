package org.cleverframe.doc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类，对应表doc_history(文档历史表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-12 22:20:47 <br/>
 */
@Entity
@Table(name = "doc_history")
@DynamicInsert
@DynamicUpdate
public class DocHistory extends IdEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;

    /**
     * 文档ID-关联doc_document(文档表)
     */
    private Long documentId;

    /**
     * 文档内容
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String content;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取 文档ID-关联doc_document(文档表)
     */
    public Long getDocumentId() {
        return documentId;
    }

    /**
     * 设置 文档ID-关联doc_document(文档表)
     */
    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
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
