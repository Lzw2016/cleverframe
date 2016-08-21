package org.cleverframe.monitor.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-20 11:53 <br/>
 */
public class BeanInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;
    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性类型
     */
    private String clazz;

    /**
     * 属性值(Json字符串)
     */
    private String jsonValue;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }
}
