package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 删除(QLScript)的请求参数对象<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-14 9:57 <br/>
 */
public class QLScriptDelVo extends BaseRequestVo {
    /**
     * 脚本名称，使用包名称+类名+方法名
     */
    @NotBlank(message = "脚本名称不能为空")
    @Length(min = 1, max = 100, message = "脚本名称长度不能操作100个字符")
    private String name;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
