package org.cleverframe.doc.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocHistory;
import org.cleverframe.doc.service.DocHistoryService;
import org.cleverframe.doc.vo.request.DocHistoryFindVo;
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
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 14:23:37 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/doc/dochistory")
public class DocHistoryController extends BaseController {

    @Autowired
    @Qualifier(DocBeanNames.DocHistoryService)
    private DocHistoryService docHistoryService;

    /**
     * 获取某个文档的历史记录 - 支持分页
     */
    @RequestMapping("/findByPage")
    @ResponseBody
    public AjaxMessage<Page<DocHistory>> findByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocHistoryFindVo docHistoryFindVo,
            BindingResult bindingResult) {
        AjaxMessage<Page<DocHistory>> ajaxMessage = new AjaxMessage<>(true, "获取文档历史成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            Page<DocHistory> page = docHistoryService.findByPage(new Page<>(request, response), docHistoryFindVo.getDocumentId());
            ajaxMessage.setResult(page);
        }
        return ajaxMessage;
    }
}
