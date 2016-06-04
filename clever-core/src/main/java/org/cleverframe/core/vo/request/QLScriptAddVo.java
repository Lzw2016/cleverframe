package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 增加(QLScript)的请求参数对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-30 17:56 <br/>
 */
public class QLScriptAddVo extends BaseRequestVo {
    /**
     * 脚本类型，可取："SQL"、"HQL"
     */
    @NotBlank(message = "脚本类型不能为空")
    @Pattern(regexp = "SQL|HQL", message = "脚本类型只能是：SQL、HQL")
    private String scriptType;

    /**
     * 查询脚本，可以使用模版技术拼接
     */
    @NotBlank(message = "脚本内容不能为空")
    @Length(min = 1, max = 2000, message = "脚本内容长度不能操作2000个字符")
    private String script;

    /**
     * 脚本名称，使用包名称+类名+方法名
     */
    @NotBlank(message = "脚本名称不能为空")
    @Length(min = 1, max = 100, message = "脚本名称长度不能操作100个字符")
    private String name;

    /**
     * 脚本功能说明
     */
    @NotBlank(message = "脚本功能说明不能为空")
    @Length(min = 1, max = 1000, message = "脚本功能说明长度不能操作1000个字符")
    private String description;

    /**
     * 备注
     */
    @Length(min = 0, max = 255, message = "备注信息长度不能操作255个字符")
    private String remarks;

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
}
