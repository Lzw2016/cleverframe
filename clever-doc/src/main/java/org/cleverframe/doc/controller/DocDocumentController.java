package org.cleverframe.doc.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.tree.BuildTreeUtils;
import org.cleverframe.common.tree.ITreeNode;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.DocJspUrlPath;
import org.cleverframe.doc.entity.DocDocument;
import org.cleverframe.doc.service.DocDocumentService;
import org.cleverframe.doc.vo.request.*;
import org.cleverframe.webui.easyui.data.TreeNodeJson;
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
import java.util.ArrayList;
import java.util.List;

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
     * 获取项目所有的文档信息，不包含文档内容 (树结构数据)
     */
    @RequestMapping("/findDocDocumentByProjectId")
    @ResponseBody
    public AjaxMessage<List<ITreeNode>> findDocDocumentByProjectId(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocDocumentQueryVo docDocumentQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<List<ITreeNode>> ajaxMessage = new AjaxMessage<>(true, "获取项目文档成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            List<DocDocument> docDocumentList = docDocumentService.findByProjectId(docDocumentQueryVo.getProjectId());
            List<ITreeNode> treeNodeList = new ArrayList<>();
            for (DocDocument docDocument : docDocumentList) {
                String iconCls = "";
                String state = "open";
                TreeNodeJson node = new TreeNodeJson(
                        docDocument.getParentId(),
                        docDocument.getId(),
                        docDocument.getFullPath(),
                        docDocument.getTitle(), iconCls, false, state);
                node.setAttributes(docDocument);
                treeNodeList.add(node);
            }
            treeNodeList = BuildTreeUtils.bulidTree(treeNodeList);
            ajaxMessage.setResult(treeNodeList);
        }
        return ajaxMessage;
    }

    /**
     * 获取文档信息 - 包含文档内容
     */
    @RequestMapping("/getDocDocument")
    @ResponseBody
    public AjaxMessage<DocDocument> getDocDocument(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocDocumentGetVo docDocumentGetVo,
            BindingResult bindingResult) {
        AjaxMessage<DocDocument> ajaxMessage = new AjaxMessage<>(true, "获取文档信息成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocDocument docDocument = docDocumentService.getDocDocument(docDocumentGetVo.getId());
            if (docDocument == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("文档不存在");
            } else {
                ajaxMessage.setResult(docDocument);
            }
        }
        return ajaxMessage;
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
