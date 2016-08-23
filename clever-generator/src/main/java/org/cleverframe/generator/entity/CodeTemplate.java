package org.cleverframe.generator.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 代码模版，扩展模版(Template)的属性
 *
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 21:19 <br/>
 */
@Entity
@Table(name = "generator_code_template")
@DynamicInsert
@DynamicUpdate
public class CodeTemplate extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 节点类型 0:模版分类
     */
    public static final Character NodeTypeCategory = '0';

    /**
     * 节点类型 1:代码模版
     */
    public static final Character NodeTypeCode = '1';

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    private Long parentId;

    /**
     * 树结构的全路径用“-”隔开,包含自己的ID
     */
    private String fullPath;

    /**
     * 代码模版名称，不能重复
     */
    private String name;

    /**
     * 节点类型(0:模版分类; 1:代码模版)
     */
    private Character nodeType;

    /**
     * 脚本模版引用，与core_template的name字段关联
     */
    private String templateRef;

    /**
     * 模版说明
     */
    private String description;

    /**
     * 模版代码语言，如：java、html、jsp、sql<br/>
     * #see {@link org.cleverframe.generator.format.CodeType}
     */
    private String codeType;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getNodeType() {
        return nodeType;
    }

    public void setNodeType(Character nodeType) {
        this.nodeType = nodeType;
    }

    public String getTemplateRef() {
        return templateRef;
    }

    public void setTemplateRef(String templateRef) {
        this.templateRef = templateRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
