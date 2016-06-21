package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 18:09 <br/>
 */
public class CodeFormatVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;


    /**
     * 代码类型，如：CodeType.HTML<br/>
     *
     * @see {@link org.cleverframe.generator.format.CodeType}
     */
    @NotBlank(message = "代码类型不能为空")
    private String codeType;

    /**
     * 需要格式化的代码
     */
    @NotBlank(message = "代码内容不能为空")
    private String code;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getCodeType() {
        return codeType;
    }

    /**
     * 代码类型，如：CodeType.HTML<br/>
     *
     * @see {@link org.cleverframe.generator.format.CodeType}
     */
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
