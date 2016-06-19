package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 12:04 <br/>
 */
public class MDictUpdateVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @NotNull(message = "数据ID不能为空")
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    @NotBlank(message = "删除标记不能为空")
    @Pattern(regexp = "1|2", message = "删除标记只能是：1(正常)、2(删除)")
    private String delFlag;

    /**
     * 备注信息
     */
    @Length(max = 255, message = "备注信息长度不能超过255个字符")
    private String remarks;

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    @NotNull(message = "父节点不能为空")
    private Long parentId;

    /**
     * 字典键
     */
    @NotBlank(message = "字典键不能为空")
    @Length(max = 100, message = "字典键长度不能超过100个字符")
    private String mdictKey;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    @Length(max = 255, message = "字典值长度不能超过255个字符")
    private String mdictValue;

    /**
     * 字典分类
     */
    @NotBlank(message = "字典分类不能为空")
    @Length(max = 100, message = "字典分类长度不能超过100个字符")
    private String mdictType;

    /**
     * 字典描述
     */
    @NotBlank(message = "字典描述不能为空")
    @Length(max = 500, message = "字典描述长度不能超过500个字符")
    private String description;

    /**
     * 排序(升序)
     */
    @NotNull(message = "字典排序不能为空")
    private Long sort;

    /*--------------------------------------------------------------
    *          getter、setter
    * -------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
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

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
