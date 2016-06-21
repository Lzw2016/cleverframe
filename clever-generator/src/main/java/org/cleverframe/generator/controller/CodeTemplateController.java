package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.entity.Template;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.entity.CodeTemplate;
import org.cleverframe.generator.service.CodeTemplateService;
import org.cleverframe.generator.vo.request.CodeTemplateAddVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 22:02 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/generator/codetemplate")
public class CodeTemplateController extends BaseController {

    @Autowired
    @Qualifier(GeneratorBeanNames.CodeTemplateService)
    private CodeTemplateService codeTemplateService;


    /**
     * 新增代码模版
     */
    @RequestMapping("/addCodeTemplate")
    @ResponseBody
    public AjaxMessage<String> addCodeTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateAddVo codeTemplateAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateAddVo, CodeTemplate.class);
        Template template = BeanMapper.mapper(codeTemplateAddVo, Template.class);
        template.setName(codeTemplateAddVo.getName());


        return ajaxMessage;
    }
}
