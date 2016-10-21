package org.cleverframe.sys.vo.response;

import org.cleverframe.common.vo.request.BaseRequestVo;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/22 3:10 <br/>
 */
public class ResourcesTreeNodeVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 被依赖的资源ID
     */
    private Long parentId;

    /**
     * 资源标题
     */
    private String title;

    /**
     * 资源URL地址
     */
    private String resourcesUrl;

    /**
     * 资源访问所需要的权限标识字符串
     */
    private String permission;

    /**
     * 资源类型（1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源）
     */
    private Character resourcesType;

    /**
     * 资源说明
     */
    private String description;

    /**
     * 依赖的资源信息
     */
    List<ResourcesTreeNodeVo> children;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourcesUrl() {
        return resourcesUrl;
    }

    public void setResourcesUrl(String resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Character getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(Character resourcesType) {
        this.resourcesType = resourcesType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResourcesTreeNodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<ResourcesTreeNodeVo> children) {
        this.children = children;
    }
}
