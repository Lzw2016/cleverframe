package org.cleverframe.core.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 模版数据表
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 15:10 <br/>
 */
@Entity
@Table(name = "core_template")
@DynamicInsert
@DynamicUpdate
public class Template extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 模版名称，不能重复
     */
    private String name;

    /**
     * 模版内容
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String content;

    /**
     * 模版语言
     */
    private String locale;

    /**
     * 模版说明
     */
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
