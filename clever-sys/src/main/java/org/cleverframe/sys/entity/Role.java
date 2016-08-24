package org.cleverframe.sys.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类，对应表sys_role(角色表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 00:13:05 <br/>
 */
@Entity
@Table(name = "sys_role")
@DynamicInsert
@DynamicUpdate
public class Role extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色说明
     */
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 角色说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 角色说明
     */
    public void setDescription(String description) {
        this.description = description;
    }

}