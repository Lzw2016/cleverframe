package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.format.CodeType;
import org.cleverframe.generator.service.CodeFormatService;
import org.cleverframe.generator.vo.request.CodeFormatVo;
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
 * 创建时间：2016-6-21 17:42 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/generator/codeformat")
public class CodeFormatController extends BaseController {

    @Autowired
    @Qualifier(GeneratorBeanNames.CodeFormatService)
    private CodeFormatService codeFormatService;

    /**
     * 格式化代码
     */
    @RequestMapping("/format")
    @ResponseBody
    public AjaxMessage<String> format(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeFormatVo codeFormatVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "格式化成功", null);
        String result = null;
        if (beanValidator(bindingResult, ajaxMessage)) {
            if (CodeType.HTML.equalsIgnoreCase(codeFormatVo.getCodeType())) {
                result = codeFormatService.htmlFormat(codeFormatVo.getCode(), ajaxMessage);
            } else if (CodeType.Java.equalsIgnoreCase(codeFormatVo.getCodeType())) {
                result = codeFormatService.javaFormat(codeFormatVo.getCode(), ajaxMessage);
            } else if (CodeType.Json.equalsIgnoreCase(codeFormatVo.getCodeType())) {
                result = codeFormatService.jsonFormat(codeFormatVo.getCode(), ajaxMessage);
            } else if (CodeType.SQL.equalsIgnoreCase(codeFormatVo.getCodeType())) {
                result = codeFormatService.sqlFormat(codeFormatVo.getCode(), ajaxMessage);
            } else if (CodeType.XML.equalsIgnoreCase(codeFormatVo.getCodeType())) {
                result = codeFormatService.xmlFormat(codeFormatVo.getCode(), ajaxMessage);
            } else {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("不支持的格式化代码[" + codeFormatVo.getCodeType() + "]");
            }
        }
        ajaxMessage.setResult(result);
        return ajaxMessage;
    }
}
