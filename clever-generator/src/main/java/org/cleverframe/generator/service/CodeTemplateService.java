package org.cleverframe.generator.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.entity.Template;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.dao.CodeTemplateDao;
import org.cleverframe.generator.entity.CodeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 22:00 <br/>
 */
@Service(GeneratorBeanNames.CodeTemplateService)
public class CodeTemplateService extends BaseService {

    @Autowired
    @Qualifier(GeneratorBeanNames.CodeTemplateDao)
    private CodeTemplateDao codeTemplateDao;

    /**
     * 增加代码模版
     *
     * @param codeTemplate 代码模版信息
     * @param template     模版对象
     * @param ajaxMessage  请求响应信息
     * @return 成功返回true，失败返回false
     */
    public boolean addCodeTemplate(CodeTemplate codeTemplate, Template template, AjaxMessage ajaxMessage) {


        return true;
    }
}
