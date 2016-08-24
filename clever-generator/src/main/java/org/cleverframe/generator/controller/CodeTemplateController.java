package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.tree.BuildTreeUtils;
import org.cleverframe.common.tree.ITreeNode;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.entity.Template;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.GeneratorJspUrlPath;
import org.cleverframe.generator.entity.CodeTemplate;
import org.cleverframe.generator.service.CodeTemplateService;
import org.cleverframe.generator.vo.request.*;
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
 * 创建时间：2016-6-21 22:02 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/generator/codetemplate")
public class CodeTemplateController extends BaseController {

    @Autowired
    @Qualifier(GeneratorBeanNames.CodeTemplateService)
    private CodeTemplateService codeTemplateService;

    @RequestMapping("/CodeTemplate" + VIEW_PAGE_SUFFIX)
    public ModelAndView getCodeTemplateJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(GeneratorJspUrlPath.CodeTemplate);
    }

    /**
     * 查询所有的代码模版，构建代码模版树
     */
    @RequestMapping("/findAllCodeTemplate")
    @ResponseBody
    public List<ITreeNode> findAllCodeTemplate(HttpServletRequest request, HttpServletResponse response) {
        String isClose = request.getParameter("isClose");
        List<CodeTemplate> codeTemplateList = codeTemplateService.findAllCodeTemplate();
        List<ITreeNode> nodes = new ArrayList<>();
        for (CodeTemplate codeTemplate : codeTemplateList) {
            String iconCls = null;
            String state = "open";
            if (CodeTemplate.NodeTypeCategory.equals(codeTemplate.getNodeType())) {
                iconCls = "icon-folderPage";
                if("true".equalsIgnoreCase(isClose)) {
                    state = "closed";
                } else {
                    state = "open";
                }
            } else if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
                iconCls = "icon-script";
                state = "open";
            }
            TreeNodeJson node = new TreeNodeJson(
                    codeTemplate.getParentId(),
                    codeTemplate.getId(),
                    codeTemplate.getFullPath(),
                    codeTemplate.getName(), iconCls, false, state);
            node.setAttributes(codeTemplate);
            nodes.add(node);
        }
        return BuildTreeUtils.bulidTree(nodes);
    }

    /**
     * 查询代码分类的子节点，构建代码模版分类树
     */
    @RequestMapping("/findChildNode")
    @ResponseBody
    public List<ITreeNode> findChildNode(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateQueryChildVo codeTemplateQueryChildVo,
            BindingResult bindingResult) {
        List<CodeTemplate> codeTemplateList = codeTemplateService.findChildNode(
                codeTemplateQueryChildVo.getFullPath(),
                codeTemplateQueryChildVo.getExcludePath(),
                true);
        List<ITreeNode> nodes = new ArrayList<>();
        // 增加根节点
        TreeNodeJson node = new TreeNodeJson(-1L, -1L, "", "根路径", "icon-folderPage", false, "open");
        nodes.add(node);
        // 加载数据库数据
        for (CodeTemplate codeTemplate : codeTemplateList) {
            String iconCls = null;
            if (CodeTemplate.NodeTypeCategory.equals(codeTemplate.getNodeType())) {
                iconCls = "icon-folderPage";
            } else if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
                iconCls = "icon-script";
            }
            node = new TreeNodeJson(
                    codeTemplate.getParentId(),
                    codeTemplate.getId(),
                    codeTemplate.getFullPath(),
                    codeTemplate.getName(), iconCls, false, "open");
            node.setAttributes(codeTemplate);
            nodes.add(node);
        }
        return BuildTreeUtils.bulidTree(nodes);
    }

    /**
     * 新增代码模版-分类
     */
    @RequestMapping("/addCodeTemplateCategory")
    @ResponseBody
    public AjaxMessage<String> addCodeTemplateCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateCategoryAddVo codeTemplateCategoryAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "新增代码模版[分类]成功", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateCategoryAddVo, CodeTemplate.class);
        codeTemplate.setNodeType(CodeTemplate.NodeTypeCategory);
        codeTemplate.setTemplateRef(null);
        codeTemplate.setCodeType("Category");
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.addCodeTemplateCategory(codeTemplate, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 新增代码模版-代码模版
     */
    @RequestMapping("/addCodeTemplateCode")
    @ResponseBody
    public AjaxMessage<String> addCodeTemplateCode(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateCodeAddVo codeTemplateCodeAddVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "新增代码模版[代码模版]成功", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateCodeAddVo, CodeTemplate.class);
        codeTemplate.setNodeType(CodeTemplate.NodeTypeCode);
        codeTemplate.setTemplateRef(codeTemplate.getName());
        Template template = BeanMapper.mapper(codeTemplateCodeAddVo, Template.class);
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.addCodeTemplateCode(codeTemplate, template, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 更新代码模版
     */
    @RequestMapping("/updateCodeTemplate")
    @ResponseBody
    public AjaxMessage<String> updateCodeTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateUpdateVo codeTemplateUpdateVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "更新代码模版成功", null);
        CodeTemplate codeTemplate = BeanMapper.mapper(codeTemplateUpdateVo, CodeTemplate.class);
        Template template = BeanMapper.mapper(codeTemplateUpdateVo, Template.class);
        template.setName(codeTemplateUpdateVo.getTemplateRef());
        // 设置ID
        codeTemplate.setId(codeTemplateUpdateVo.getCodeTemplateId());
        template.setId(codeTemplateUpdateVo.getTemplateId());
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.updateCodeTemplate(codeTemplate, template, ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 删除代码模版
     */
    @RequestMapping("/delCodeTemplate")
    @ResponseBody
    public AjaxMessage<String> delCodeTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CodeTemplateDelVo codeTemplateDelVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "删除代码模版成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            codeTemplateService.delCodeTemplate(codeTemplateDelVo.getCodeTemplateName(), ajaxMessage);
        }
        return ajaxMessage;
    }

}
