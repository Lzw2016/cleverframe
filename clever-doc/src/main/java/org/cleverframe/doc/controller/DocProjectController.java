package org.cleverframe.doc.controller;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.DocJspUrlPath;
import org.cleverframe.doc.entity.DocProject;
import org.cleverframe.doc.service.DocProjectService;
import org.cleverframe.doc.vo.request.*;
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
@RequestMapping(value = "/${base.mvcPath}/doc/docproject")
public class DocProjectController extends BaseController {

    @Autowired
    @Qualifier(DocBeanNames.DocProjectService)
    private DocProjectService docProjectService;

    @RequestMapping("/DocProject" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDocProjectJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(DocJspUrlPath.DocProject);
    }

    /**
     * 分页查询文档项目
     */
    @RequestMapping("/queryDocProject")
    @ResponseBody
    public AjaxMessage<Page<DocProject>> queryDocProject(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocProjectQueryVo docProjectQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<Page<DocProject>> ajaxMessage = new AjaxMessage<>(true, "查询文档项目成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            if (StringUtils.isNotBlank(docProjectQueryVo.getName())) {
                docProjectQueryVo.setName("%" + docProjectQueryVo.getName() + "%");
            }
            Page<DocProject> page = docProjectService.queryDocProject(new Page<>(request, response), docProjectQueryVo.getName(), docProjectQueryVo.getCreateBy());
            ajaxMessage.setResult(page);
        }
        return ajaxMessage;
    }

    /**
     * 获取文档项目信息
     */
    @RequestMapping("/getDocProject")
    @ResponseBody
    public AjaxMessage<DocProject> getDocProject(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DocProjectGetVo docProjectGetVo,
            BindingResult bindingResult) {
        AjaxMessage<DocProject> ajaxMessage = new AjaxMessage<>(true, "获取文档项目信息成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            DocProject docProject = docProjectService.getDocProject(docProjectGetVo.getId());
            if (docProject == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("获取文档项目信息不存在");
            } else {
                ajaxMessage.setResult(docProject);
            }
        }
        return ajaxMessage;
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
        addXSSExcludeUrl(request);
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
