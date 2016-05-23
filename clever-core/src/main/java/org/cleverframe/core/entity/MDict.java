package org.cleverframe.core.entity;

import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 树结构字典
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 15:12 <br/>
 */
@Entity
@Table(name = "core_mdict")
@DynamicInsert
@DynamicUpdate
public class MDict extends IdEntity {
    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 树结构的全路径用“-”隔开
     */
    private String fullPath;

    /**
     * 字典名称
     */
    private String mdictKey;

    /**
     * 字典值
     */
    private String mdictValue;

    /**
     * 字典类型
     */
    private String mdictType;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

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

    public String getMdictKey() {
        return mdictKey;
    }

    public void setMdictKey(String mdictKey) {
        this.mdictKey = mdictKey;
    }

    public String getMdictValue() {
        return mdictValue;
    }

    public void setMdictValue(String mdictValue) {
        this.mdictValue = mdictValue;
    }

    public String getMdictType() {
        return mdictType;
    }

    public void setMdictType(String mdictType) {
        this.mdictType = mdictType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
