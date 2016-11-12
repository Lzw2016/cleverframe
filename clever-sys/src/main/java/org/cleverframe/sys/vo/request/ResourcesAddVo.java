package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/21 22:25 <br/>
 */
public class ResourcesAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 资源标题
     */
    @NotBlank(message = "资源标题不能为空")
    @Length(min = 1, max = 255, message = "资源标题值长度不能超过255个字符")
    private String title;

    /**
     * 需要授权才允许访问（1：需要；2：不需要）
     */
    @NotNull(message = "是否需要授权才允许访问不能为空")
    private Character needAuthorization;

    /**
     * 资源URL地址
     */
    @NotBlank(message = "资源URL地址不能为空")
    @Length(min = 1, max = 255, message = "资源URL地址值长度不能超过255个字符")
    private String resourcesUrl;

    /**
     * 资源访问所需要的权限标识字符串
     */
    @NotBlank(message = "权限标识不能为空")
    @Length(min = 1, max = 255, message = "权限标识值长度不能超过255个字符")
    private String permission;

    /**
     * 资源类型（1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源）
     */
    @NotBlank(message = "资源类型不能为空")
    @Pattern(regexp = "1|2|3", message = "资源类型必须取值(1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源)")
    private String resourcesType;

    /**
     * 资源说明
     */
    @NotBlank(message = "资源说明不能为空")
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Character getNeedAuthorization() {
        return needAuthorization;
    }

    public void setNeedAuthorization(Character needAuthorization) {
        this.needAuthorization = needAuthorization;
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

    public String getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(String resourcesType) {
        this.resourcesType = resourcesType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
