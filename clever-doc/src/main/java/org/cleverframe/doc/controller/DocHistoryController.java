package org.cleverframe.doc.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.service.DocHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 14:23:37 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/moduleName/dochistory")
public class DocHistoryController extends BaseController {

    @Autowired
    @Qualifier(DocBeanNames.DocHistoryService)
    private DocHistoryService docHistoryService;

}
