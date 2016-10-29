package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/29 20:12 <br/>
 */
public class RoleUpdateVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "更新数据的id不能为空")
    private Long id;

    /**
     * 备注
     */
    @Length(min = 1, max = 255, message = "备注信息值长度不能超过255个字符")
    private String remarks;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 1, max = 50, message = "角色名称值长度不能超过50个字符")
    private String name;

    /**
     * 角色说明
     */
    @NotBlank(message = "角色说明不能为空")
    @Length(min = 1, max = 2000, message = "角色说明值长度不能超过2000个字符")
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
