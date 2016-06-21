package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.service.CodeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
