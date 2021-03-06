package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.entity.Role;
import org.cleverframe.sys.service.RoleService;
import org.cleverframe.sys.vo.request.*;
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
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:36:04 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/sys/role")
public class RoleController extends BaseController {

    @Autowired
    @Qualifier(SysBeanNames.RoleService)
    private RoleService roleService;

    @RequestMapping("/Role" + VIEW_PAGE_SUFFIX)
    public ModelAndView getRoleJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.Role);
    }

    @RequestMapping("/RoleResources" + VIEW_PAGE_SUFFIX)
    public ModelAndView getRoleResourcesJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.RoleResources);
    }

    /**
     * 分页查询
     */
    @RequestMapping("/findByPage")
    @ResponseBody
    public DataGridJson<Role> findByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleQueryVo roleQueryVo,
            BindingResult bindingResult) {
        DataGridJson<Role> json = new DataGridJson<>();
        Page<Role> page = roleService.findByPage(
                new Page<>(request, response),
                roleQueryVo.getUuid(),
                roleQueryVo.getId(),
                roleQueryVo.getDelFlag(),
                roleQueryVo.getName());
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }

    /**
     * 增加角色信息
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public AjaxMessage<String> addRole(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleAddVo roleAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "新增角色成功", null);
        Role role = BeanMapper.mapper(roleAddVo, Role.class);
        if (beanValidator(bindingResult, message)) {
            if (!roleService.saveRole(role)) {
                message.setSuccess(false);
                message.setFailMessage("新增角色失败");
            }
        }
        return message;
    }

    /**
     * 更新角色信息
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxMessage<String> updateRole(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleUpdateVo roleUpdateVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "更新角色成功", null);
        Role role = BeanMapper.mapper(roleUpdateVo, Role.class);
        if (beanValidator(bindingResult, message)) {
            if (!roleService.updateRole(role)) {
                message.setSuccess(false);
                message.setFailMessage("更新角色失败");
            }
        }
        return message;
    }

    /**
     * 删除角色信息 (软删除)
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxMessage<String> deleteRole(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleDeleteVo reRoleDeleteVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "删除角色成功", null);
        Role role = BeanMapper.mapper(reRoleDeleteVo, Role.class);
        if (beanValidator(bindingResult, message)) {
            if (!roleService.deleteRole(role)) {
                message.setSuccess(false);
                message.setFailMessage("删除角色失败");
            }
        }
        return message;
    }

    /**
     * 查询角色资源数据 (不分页)
     */
    @RequestMapping("/findResourcesByRole")
    @ResponseBody
    public AjaxMessage<List<Resources>> findResourcesByRole(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleResourcesQueryVo roleResourcesQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<List<Resources>> message = new AjaxMessage<>(true, "查询角色资源数据成功", null);
        List<Resources> list = roleService.findResourcesByRole(roleResourcesQueryVo.getId());
        message.setResult(list);
        return message;
    }

    /**
     * 角色添加资源
     */
    @RequestMapping("/addRoleResources")
    @ResponseBody
    public AjaxMessage<String> addRoleResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleResourcesAddVo roleResourcesAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "角色添加资源成功", null);
        if (beanValidator(bindingResult, message)) {
            if (!roleService.addRoleResources(roleResourcesAddVo.getRoleId(), roleResourcesAddVo.getResourcesId())) {
                message.setSuccess(false);
                message.setFailMessage("角色添加资源失败");
            }
        }
        return message;
    }

    /**
     * 角色移除资源
     */
    @RequestMapping("/deleteRoleResources")
    @ResponseBody
    public AjaxMessage<String> deleteRoleResources(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RoleResourcesDeleteVo roleResourcesDeleteVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "角色移除资源成功", null);
        if (beanValidator(bindingResult, message)) {
            if (!roleService.deleteRoleResources(roleResourcesDeleteVo.getRoleId(), roleResourcesDeleteVo.getResourcesId())) {
                message.setSuccess(false);
                message.setFailMessage("角色移除资源失败");
            }
        }
        return message;
    }
}
