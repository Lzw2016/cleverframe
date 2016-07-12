package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 22:07 <br/>
 */
public class DictAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 备注信息
     */
    @Length(max = 255, message = "备注信息长度不能超过255个字符")
    private String remarks;

    /**
     * 字典键
     */
    @NotBlank(message = "字典键不能为空")
    @Length(max = 100, message = "字典键长度不能超过100个字符")
    private String dictKey;

    /**
     * 字典数据值
     */
    @NotBlank(message = "字典数据值不能为空")
    @Length(max = 255, message = "字典数据值长度不能超过255个字符")
    private String dictValue;

    /**
     * 字典分类
     */
    @NotBlank(message = "字典分类不能为空")
    @Length(max = 100, message = "字典分类长度不能超过100个字符")
    private String dictType;

    /**
     * 字典描述
     */
    @NotBlank(message = "字典描述不能为空")
    @Length(max = 500, message = "字典描述长度不能超过500个字符")
    private String description;

    /**
     * 字典排序
     */
    @NotNull(message = "字典排序不能为空")
    private Long sort;

    /*--------------------------------------------------------------
    *          getter、setter
    * -------------------------------------------------------------*/

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

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

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
