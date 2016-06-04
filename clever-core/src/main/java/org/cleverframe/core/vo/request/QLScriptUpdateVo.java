package org.cleverframe.core.vo.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 更新(QLScript)的请求参数对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-30 22:47 <br/>
 * TODO 加校验，加ID？
 */
public class QLScriptUpdateVo {
    /**
     * 脚本类型，可取："SQL"、"HQL"
     */
    @Pattern(regexp = "SQL|HQL", message = "脚本类型只能是：SQL、HQL")
    private String scriptType;

    /**
     * 查询脚本，可以使用模版技术拼接
     */
//    @NotNull
    @Length(min = 1, max = 2000, message = "脚本内容不能为空，而且脚本长度不能操作2000个字符")
    private String script;

    /**
     * 脚本名称，使用包名称+类名+方法名
     */
    @Length(min = 1, max = 100, message = "脚本名称不能为空，而且脚本长度不能操作100个字符")
    private String name;

    /**
     * 脚本功能说明
     */
    @Length(min = 1, max = 1000, message = "脚本功能说明不能为空，而且脚本长度不能操作1000个字符")
    private String description;

    /**
     * 备注
     */
    @Length(min = 0, max = 255, message = "备注信息长度不能操作255个字符")
    private String remarks;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    @Pattern(regexp = "1|2", message = "删除标记只能是：1(正常)、2(删除)")
    private String delFlag;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
