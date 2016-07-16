package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.entity.Template;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.GeneratorJspUrlPath;
import org.cleverframe.generator.entity.CodeTemplate;
import org.cleverframe.generator.service.CodeTemplateService;
import org.cleverframe.generator.vo.request.CodeTemplateCategoryAddVo;
import org.cleverframe.generator.vo.request.CodeTemplateCodeAddVo;
import org.cleverframe.generator.vo.request.CodeTemplateUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/CodeTemplate" + VIEW_PAGE_SUFFIX)
    public ModelAndView getCodeTemplateJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(GeneratorJspUrlPath.CodeTemplate);
    }

    /**
     * 新增代码模版-分类
     */
    @RequestMapping("/addCodeTemplateCategory")
    @ResponseBody
    public AjaxMessage<String> addCodeTemplateCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateCategoryAddVo codeTemplateCategoryAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "新增代码模版[分类]成功", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateCategoryAddVo, CodeTemplate.class);
        codeTemplate.setNodeType(CodeTemplate.NodeTypeCategory);
        codeTemplate.setTemplateRef(null);
        codeTemplate.setCodeType("Category");
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.addCodeTemplateCategory(codeTemplate, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 新增代码模版-代码模版
     */
    @RequestMapping("/addCodeTemplateCode")
    @ResponseBody
    public AjaxMessage<String> addCodeTemplateCode(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateCodeAddVo codeTemplateCodeAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "新增代码模版[代码模版]成功", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateCodeAddVo, CodeTemplate.class);
        codeTemplate.setNodeType(CodeTemplate.NodeTypeCode);
        codeTemplate.setTemplateRef(codeTemplate.getName());
        Template template = BeanMapper.mapper(codeTemplateCodeAddVo, Template.class);
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.addCodeTemplateCode(codeTemplate, template, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 更新代码模版
     */
    @RequestMapping("/updateCodeTemplate")
    @ResponseBody
    public AjaxMessage<String> updateCodeTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateUpdateVo codeTemplateUpdateVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "更新代码模版", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateUpdateVo, CodeTemplate.class);
        Template template = BeanMapper.mapper(codeTemplateUpdateVo, Template.class);
        template.setName(codeTemplateUpdateVo.getName());
        template.setId(null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.updateCodeTemplate(codeTemplate, template, ajaxMessage);
        }
        return ajaxMessage;
    }

}
