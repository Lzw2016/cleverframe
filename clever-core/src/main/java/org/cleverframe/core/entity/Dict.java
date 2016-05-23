package org.cleverframe.core.entity;

import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 15:09 <br/>
 */
@Entity
@Table(name = "core_dict")
@DynamicInsert
@DynamicUpdate
public class Dict extends IdEntity {
    /**
     * 字典键
     */
    private String dictKey;

    /**
     * 字典数据值
     */
    private String dictValue;

    /**
     * 字典分类
     */
    private String dictType;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /*--------------------------------------------------------------
    *          getter、setter
    * -------------------------------------------------------------*/

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
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
