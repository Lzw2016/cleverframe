package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/13 20:59 <br/>
 */
public class DocProjectUpdateVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "数据的id不能为空")
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    @Pattern(regexp = "1|2", message = "删除标记只能是：1(正常)、2(删除)")
    private String delFlag;

    /**
     * 备注
     */
    @Length(max = 255, message = "备注信息值长度不能超过255个字符")
    private String remarks;
    /**
     * 文档项目名称
     */
    @Length(max = 100, message = "文档项目名称长度不能超过100个字符")
    private String name;

    /**
     * 文档介绍和说明
     */
    @Length(max = 2000000, message = "文档目录长度不能超过2000000个字符")
    private String readme;

    /**
     * 文档目录
     */
    @Length(max = 2000000, message = "文档目录长度不能超过2000000个字符")
    private String summary;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
