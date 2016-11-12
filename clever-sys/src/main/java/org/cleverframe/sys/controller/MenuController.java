package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.entity.Menu;
import org.cleverframe.sys.service.MenuService;
import org.cleverframe.sys.vo.request.*;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.cleverframe.webui.easyui.data.TreeGridNodeJson;
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
import java.util.Map;

/**
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:47:29 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    @Qualifier(SysBeanNames.MenuService)
    private MenuService menuService;

    @RequestMapping("/Menu" + VIEW_PAGE_SUFFIX)
    public ModelAndView getMenuJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.Menu);
    }

    /**
     * 分页查询
     */
    @RequestMapping("/findByPage")
    @ResponseBody
    public DataGridJson<Menu> findByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MenuQueryVo menuQueryVo,
            BindingResult bindingResult) {
        DataGridJson<Menu> json = new DataGridJson<>();
        Page<Menu> page = menuService.findByPage(new Page<>(request, response), menuQueryVo.getMenuType(), menuQueryVo.getName(), menuQueryVo.getOpenMode());
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }

    /**
     * 查询所有菜单类型
     */
    @RequestMapping("/findAllMenuType")
    @ResponseBody
    public List<Map<String, Object>> findAllMenuType(HttpServletRequest request, HttpServletResponse response) {
        return menuService.findAllMenuType();
    }

    /**
     * 查询菜单树
     */
    @RequestMapping("/findMenuTreeByType")
    @ResponseBody
    public Object findMenuTreeByType(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MenuTreeQueryVo menuTreeQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "查询菜单树成功", null);
        if (!beanValidator(bindingResult, message)) {
            return message;
        }
        DataGridJson<TreeGridNodeJson<Menu>> treeGrid = new DataGridJson<>();
        List<Menu> menuList = menuService.findMenuByType(menuTreeQueryVo.getMenuType());
        for (Menu menu : menuList) {
            TreeGridNodeJson<Menu> node = new TreeGridNodeJson<>(menu.getParentId(), menu);
            treeGrid.addRow(node);
        }
        return treeGrid;
    }

    /**
     * 增加菜单信息
     */
    @RequestMapping("/addMenu")
    @ResponseBody
    public AjaxMessage<String> addMenu(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MenuAddVo menuAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "新增菜单成功", null);
        if (beanValidator(bindingResult, message)) {
            Menu menu = BeanMapper.mapper(menuAddVo, Menu.class);
            menuService.saveMenu(message, menu);
        }
        return message;
    }

    /**
     * 更新菜单信息
     */
    @RequestMapping("/updateMenu")
    @ResponseBody
    public AjaxMessage<String> updateMenu(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MenuUpdateVo menuUpdateVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "更新菜单成功", null);
        if (beanValidator(bindingResult, message)) {
            Menu menu = BeanMapper.mapper(menuUpdateVo, Menu.class);
            if (!menuService.updateMenu(menu)) {
                message.setSuccess(false);
                message.setFailMessage("更新菜单失败");
            }
        }
        return message;
    }

    /**
     * 删除菜单
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public AjaxMessage<String> deleteMenu(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MenuDeleteVo menuDeleteVoe,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "删除菜单成功", null);
        if (beanValidator(bindingResult, message)) {
            menuService.deleteMenu(message, menuDeleteVoe.getId());
        }
        return message;
    }
}
