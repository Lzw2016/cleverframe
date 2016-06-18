package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.service.MDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 23:08 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping("/${mvcPath}/core/mdict")
public class MDictController extends BaseController {

    @Autowired
    @Qualifier(CoreBeanNames.MDictService)
    private MDictService mDictService;
}
