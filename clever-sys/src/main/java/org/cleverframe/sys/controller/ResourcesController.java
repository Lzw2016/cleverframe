package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.service.ResourcesService;
import org.cleverframe.sys.vo.request.*;
import org.cleverframe.sys.vo.response.ResourcesTreeNodeVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
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
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/21 17:43 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/sys/resources")
public class ResourcesController extends BaseController {

    @Autowired
    @Qualifier(SysBeanNames.ResourcesService)
    private ResourcesService resourcesService;

    @RequestMapping("/Resources" + VIEW_PAGE_SUFFIX)
    public ModelAndView getResourcesJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.Resources);
    }

    @RequestMapping("/DependenceResources" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDependenceResourcesJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.DependenceResources);
    }

    /**
     * 分页查询
     */
    @RequestMapping("/findByPage")
    @ResponseBody
    public DataGridJson<Resources> findByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ResourcesQueryVo resourcesQueryVo,
            BindingResult bindingResult) {
        DataGridJson<Resources> json = new DataGridJson<>();
        Page<Resources> page = resourcesService.findByPage(
                new Page<>(request, response),
                resourcesQueryVo.getTitle(),
                resourcesQueryVo.getResourcesUrl(),
                resourcesQueryVo.getPermission(),
                resourcesQueryVo.getResourcesType());
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }

    /**
     * 增加资源信息
     */
    @RequestMapping("/addResources")
    @ResponseBody
    public AjaxMessage<String> addResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ResourcesAddVo resourcesAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "资源信息保存成功", null);
        Resources resources = BeanMapper.mapper(resourcesAddVo, Resources.class);
        if (beanValidator(bindingResult, message)) {
            resourcesService.addResources(resources);
        }
        return message;
    }

    /**
     * 增加资源信息
     */
    @RequestMapping("/updateResources")
    @ResponseBody
    public AjaxMessage<String> updateResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ResourcesUpdateVo resourcesUpdateVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "资源信息更新成功", null);
        Resources resources = BeanMapper.mapper(resourcesUpdateVo, Resources.class);
        if (beanValidator(bindingResult, message)) {
            resourcesService.updateResources(resources);
        }
        return message;
    }

    /**
     * 增加资源信息
     */
    @RequestMapping("/deleteResources")
    @ResponseBody
    public AjaxMessage<String> deleteResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ResourcesDeleteVo resourcesDeleteVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "资源信息删除成功", null);
        if (beanValidator(bindingResult, message)) {
            boolean flag = resourcesService.deleteResources(resourcesDeleteVo.getId());
            if (!flag) {
                message.setSuccess(false);
                message.setFailMessage("资源信息删除失败");
            }
        }
        return message;
    }

    /**
     * 查询一个页面资源的所有依赖资源(不需要分页)
     */
    @RequestMapping("/findDependenceResources")
    @ResponseBody
    public AjaxMessage<List<Resources>> findDependenceResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DependenceResourcesQueryVo dependenceResourcesQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<List<Resources>> message = new AjaxMessage<>();
        List<Resources> list = null;
        if (beanValidator(bindingResult, message)) {
            list = resourcesService.findDependenceResources(dependenceResourcesQueryVo.getId());
        }
        message.setResult(list);
        return message;
    }

    /**
     * 为页面资源增加一个依赖资源
     */
    @RequestMapping("/addDependenceResources")
    @ResponseBody
    public AjaxMessage<String> addDependenceResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DependenceResourcesAddVo dependenceResourcesAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "增加依赖资源成功", null);
        if (beanValidator(bindingResult, message)) {
            boolean flag = resourcesService.addDependenceResources(dependenceResourcesAddVo.getResourcesId(), dependenceResourcesAddVo.getDependenceResourcesId());
            if (!flag) {
                message.setSuccess(false);
                message.setFailMessage("增加依赖资源成功失败");
            }
        }
        return message;
    }

    /**
     * 为页面资源删除一个依赖资源
     */
    @RequestMapping("/deleteDependenceResources")
    @ResponseBody
    public AjaxMessage<String> deleteDependenceResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DependenceResourcesDeleteVo dependenceResourcesDeleteVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "删除依赖资源成功", null);
        if (beanValidator(bindingResult, message)) {
            boolean flag = resourcesService.deleteDependenceResources(dependenceResourcesDeleteVo.getResourcesId(), dependenceResourcesDeleteVo.getDependenceResourcesId());
            if (!flag) {
                message.setSuccess(false);
                message.setFailMessage("删除依赖资源成功失败");
            }
        }
        return message;
    }

    /**
     * 查询资源依赖树(查询系统所有资源:只分两级，页面资源和后台资源)
     */
    @RequestMapping("/findResourcesTree")
    @ResponseBody
    public List<ResourcesTreeNodeVo> findResourcesTree(HttpServletRequest request, HttpServletResponse response) {
        return resourcesService.findResourcesTree();
    }
}
