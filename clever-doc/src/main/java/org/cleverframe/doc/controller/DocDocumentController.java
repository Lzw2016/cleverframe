package org.cleverframe.doc.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.DocJspUrlPath;
import org.cleverframe.doc.entity.DocDocument;
import org.cleverframe.doc.service.DocDocumentService;
import org.cleverframe.doc.vo.request.DocDocumentAddVo;
import org.cleverframe.doc.vo.request.DocDocumentDelVo;
import org.cleverframe.doc.vo.request.DocDocumentRevertVo;
import org.cleverframe.doc.vo.request.DocDocumentUpdateVo;
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
 * 创建时间：2017/1/12 22:29 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/doc/docdocument")
public class DocDocumentController extends BaseController {

    @Autowired
    @Qualifier(DocBeanNames.DocDocumentService)
    private DocDocumentService docDocumentService;

    @RequestMapping("/DocumentEdit" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDocumentEditJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(DocJspUrlPath.DocumentEdit);
    }

    /**
     * 新增文档
     */
    @RequestMapping("/addDocDocument")
    @ResponseBody
    public AjaxMessage<DocDocument> addDocDocument(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocDocumentAddVo docDocumentAddVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<DocDocument> ajaxMessage = new AjaxMessage<>(true, "新增文档成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocDocument docDocument = BeanMapper.mapper(docDocumentAddVo, DocDocument.class);
            docDocumentService.addDocDocument(docDocument, ajaxMessage);
            ajaxMessage.setResult(docDocument);
        }
        return ajaxMessage;
    }

    /**
     * 更新文档
     */
    @RequestMapping("/updateDocDocument")
    @ResponseBody
    public AjaxMessage<DocDocument> updateDocDocument(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocDocumentUpdateVo docDocumentUpdateVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<DocDocument> ajaxMessage = new AjaxMessage<>(true, "更新文档成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocDocument docDocument = BeanMapper.mapper(docDocumentUpdateVo, DocDocument.class);
            docDocumentService.updateDocDocument(docDocument, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 删除文档 - 数据库删除数据
     */
    @RequestMapping("/delDocDocument")
    @ResponseBody
    public AjaxMessage<DocDocument> delDocDocument(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocDocumentDelVo docDocumentDelVo,
            BindingResult bindingResult) {
        AjaxMessage<DocDocument> ajaxMessage = new AjaxMessage<>(true, "删除文档成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocDocument docDocument = BeanMapper.mapper(docDocumentDelVo, DocDocument.class);
            docDocumentService.delDocDocument(docDocument, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 还原文档内容
     */
    @RequestMapping("/revertDocDocument")
    @ResponseBody
    public AjaxMessage<DocDocument> revertDocDocument(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocDocumentRevertVo docDocumentRevertVo,
            BindingResult bindingResult) {
        AjaxMessage<DocDocument> ajaxMessage = new AjaxMessage<>(true, "文档内容还原成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            docDocumentService.revertDocDocument(docDocumentRevertVo.getDocumentId(), docDocumentRevertVo.getHistoryId(), ajaxMessage);
        }
        return ajaxMessage;
    }
}
