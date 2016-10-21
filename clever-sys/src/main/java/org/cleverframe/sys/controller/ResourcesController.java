package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.service.ResourcesService;
import org.cleverframe.sys.vo.request.ResourcesAddVo;
import org.cleverframe.sys.vo.request.ResourcesDeleteVo;
import org.cleverframe.sys.vo.request.ResourcesQueryVo;
import org.cleverframe.sys.vo.request.ResourcesUpdateVo;
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

}
