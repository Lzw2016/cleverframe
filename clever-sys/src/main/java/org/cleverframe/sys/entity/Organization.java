package org.cleverframe.sys.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类，对应表sys_organization(组织机构表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 00:09:48 <br/>
 */
@Entity
@Table(name = "sys_organization")
@DynamicInsert
@DynamicUpdate
public class Organization extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父级机构ID,根节点的父级机构ID是：-1
     */
    private Long parentId;

    /**
     * 树结构的全路径用“-”隔开,包含自己的ID
     */
    private String fullPath;

    /**
     * 机构编码，如：001002...
     */
    private String code;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构类型,从数据字典中获取（1: 集团；2：区域；3：公司；4：部门；5：小组）
     */
    private Character orgType;

    /**
     * 机构地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 负责人
     */
    private String master;

    /**
     * 电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    private String email;
    
    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 父级机构ID,根节点的父级机构ID是：-1
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置 父级机构ID,根节点的父级机构ID是：-1
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
     * 获取 机构编码，如：001002...
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 机构编码，如：001002...
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 机构名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 机构名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 机构类型,从数据字典中获取（1: 集团；2：区域；3：公司；4：部门；5：小组）
     */
    public Character getOrgType() {
        return orgType;
    }

    /**
     * 设置 机构类型,从数据字典中获取（1: 集团；2：区域；3：公司；4：部门；5：小组）
     */
    public void setOrgType(Character orgType) {
        this.orgType = orgType;
    }

    /**
     * 获取 机构地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置 机构地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取 邮政编码
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置 邮政编码
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取 负责人
     */
    public String getMaster() {
        return master;
    }

    /**
     * 设置 负责人
     */
    public void setMaster(String master) {
        this.master = master;
    }

    /**
     * 获取 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

}