package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-11 11:41 <br/>
 */
public class DictQueryByTypeVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字典分类
     */
    @NotBlank(message = "字典分类不能为空")
    @Length(max = 100, message = "字典分类长度不能超过100个字符")
    private String dictType;

    /**
     * 是否有全选选项，默认 false
     */
    private Boolean hasSelectAll = false;

    /**
     * 全选选项显示名称，默认：“全部”
     */
    private String selectAllKey = "全部";

    /**
     * 全选选项值，默认空字符串
     */
    private String selectAllValue = "";

    /**
     * 默认选中的key
     */
    private String defaultSelectKey;

    /*--------------------------------------------------------------
    *          getter、setter
    * -------------------------------------------------------------*/

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public Boolean getHasSelectAll() {
        return hasSelectAll;
    }

    public void setHasSelectAll(Boolean hasSelectAll) {
        this.hasSelectAll = hasSelectAll;
    }

    public String getSelectAllKey() {
        return selectAllKey;
    }

    public void setSelectAllKey(String selectAllKey) {
        this.selectAllKey = selectAllKey;
    }

    public String getSelectAllValue() {
        return selectAllValue;
    }

    public void setSelectAllValue(String selectAllValue) {
        this.selectAllValue = selectAllValue;
    }

    public String getDefaultSelectKey() {
        return defaultSelectKey;
    }

    public void setDefaultSelectKey(String defaultSelectKey) {
        this.defaultSelectKey = defaultSelectKey;
    }
}
