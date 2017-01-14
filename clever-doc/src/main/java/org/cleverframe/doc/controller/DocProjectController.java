package org.cleverframe.doc.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.DocJspUrlPath;
import org.cleverframe.doc.entity.DocProject;
import org.cleverframe.doc.service.DocProjectService;
import org.cleverframe.doc.vo.request.DocProjectAddVo;
import org.cleverframe.doc.vo.request.DocProjectDelVo;
import org.cleverframe.doc.vo.request.DocProjectUpdateVo;
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
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 14:28:06 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/moduleName/docproject")
public class DocProjectController extends BaseController {

    @Autowired
    @Qualifier(DocBeanNames.DocProjectService)
    private DocProjectService docProjectService;

    @RequestMapping("/DocProject" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDocProjectJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(DocJspUrlPath.DocProject);
    }

    /**
     * 新增文档项目
     */
    @RequestMapping("/addDocProject")
    @ResponseBody
    public AjaxMessage<DocProject> addDocProject(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocProjectAddVo docProjectAddVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<DocProject> ajaxMessage = new AjaxMessage<>(true, "新增文档项目成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocProject docProject = BeanMapper.mapper(docProjectAddVo, DocProject.class);
            docProjectService.addDocProject(docProject, ajaxMessage);
            ajaxMessage.setResult(docProject);
        }
        return ajaxMessage;
    }

    /**
     * 更新文档项目
     */
    @RequestMapping("/updateDocProject")
    @ResponseBody
    public AjaxMessage<DocProject> updateDocProject(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocProjectUpdateVo docProjectUpdateVo,
            BindingResult bindingResult) {
        AjaxMessage<DocProject> ajaxMessage = new AjaxMessage<>(true, "更新文档项目成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocProject docProject = BeanMapper.mapper(docProjectUpdateVo, DocProject.class);
            docProjectService.updateDocProject(docProject, ajaxMessage);
            ajaxMessage.setResult(docProject);
        }
        return ajaxMessage;
    }

    /**
     * 删除项目文档 - 硬删除所有文档、文档历史
     */
    @RequestMapping("/delDocProject")
    @ResponseBody
    public AjaxMessage<DocProject> delDocProject(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocProjectDelVo docProjectDelVo,
            BindingResult bindingResult) {
        AjaxMessage<DocProject> ajaxMessage = new AjaxMessage<>(true, "删除文档项目成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            docProjectService.delDocProject(docProjectDelVo.getId(), ajaxMessage);
        }
        return ajaxMessage;
    }
}
