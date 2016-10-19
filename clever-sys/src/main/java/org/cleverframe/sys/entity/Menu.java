package org.cleverframe.sys.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类，对应表sys_menu(菜单表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 00:11:49 <br/>
 */
@Entity
@Table(name = "sys_menu")
@DynamicInsert
@DynamicUpdate
public class Menu extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    private Long parentId;

    /**
     * 树结构的全路径用“-”隔开,包含自己的ID
     */
    private String fullPath;

    /**
     * 菜单类型（1：系统模块菜单，2：个人快捷菜单）
     */
    private Character menuType;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 资源ID,关联表:core_resources
     */
    private Long resourcesId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)
     */
    private Character openMode;

    /**
     * 排序(升序)
     */
    private Integer sort;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

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
     * 获取 菜单类型（1：系统模块菜单，2：个人快捷菜单）
     */
    public Character getMenuType() {
        return menuType;
    }

    /**
     * 设置 菜单类型（1：系统模块菜单，2：个人快捷菜单）
     */
    public void setMenuType(Character menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 资源ID,关联表:core_resources
     */
    public Long getResourcesId() {
        return resourcesId;
    }

    /**
     * 设置 资源ID,关联表:core_resources
     */
    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    /**
     * 获取 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取 菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)
     */
    public Character getOpenMode() {
        return openMode;
    }

    /**
     * 设置 菜单打开方式(1:Table叶签, 2:浏览器叶签, 3:浏览器新窗口)
     */
    public void setOpenMode(Character openMode) {
        this.openMode = openMode;
    }

    /**
     * 获取 排序(升序)
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置 排序(升序)
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

}