package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
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
            result = codeFormatService.codeFormat(codeFormatVo.getCodeType(), codeFormatVo.getCode(), ajaxMessage);
        }
        ajaxMessage.setResult(result);
        return ajaxMessage;
    }
}
