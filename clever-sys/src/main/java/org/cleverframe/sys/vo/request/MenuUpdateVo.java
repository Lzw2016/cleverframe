package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/5 13:07 <br/>
 */
public class MenuUpdateVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "数据的id不能为空")
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    private Character delFlag;

    /**
     * 备注
     */
    @Length(max = 255, message = "备注信息值长度不能超过255个字符")
    private String remarks;

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    private Long parentId;

    /**
     * 菜单类型
     */
    @NotNull(message = "菜单类型不能为空")
    @Length(min = 1, max = 255, message = "菜单类型值长度不能超过255个字符")
    private String menuType;

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
    @Length(min = 1, max = 50, message = "图标值长度不能超过50个字符")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Character delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Character getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Character openMode) {
        this.openMode = openMode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
