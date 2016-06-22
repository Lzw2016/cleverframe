package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.service.GeneratorCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:48 <br/>
 */

@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/generator/generatorcode")
public class GeneratorCodeController extends BaseController {

    @Autowired
    @Qualifier(GeneratorBeanNames.GeneratorCodeService)
    private GeneratorCodeService generatorCodeService;
}
