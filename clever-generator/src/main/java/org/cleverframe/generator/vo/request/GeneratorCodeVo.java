package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import java.util.List;
import java.util.Map;

/**
 * 生成代码请求数据封装
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:20 <br/>
 */
public class GeneratorCodeVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 模版数据
     */
    private Map<String, Object> templateData;

    /**
     * 模版名称，用于查找模版
     */
    private List<String> templateNames;

    /**
     * 自定义模版内容,key=TemplateName,value=TemplateContent
     */
    private Map<String, String> templateContent;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Map<String, Object> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }

    public List<String> getTemplateNames() {
        return templateNames;
    }

    public void setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
    }

    public Map<String, String> getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(Map<String, String> templateContent) {
        this.templateContent = templateContent;
    }
}
