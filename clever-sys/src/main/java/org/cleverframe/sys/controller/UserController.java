package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.SysJspUrlPath;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.service.UserService;
import org.cleverframe.sys.vo.request.UserAddVo;
import org.cleverframe.sys.vo.request.UserDeleteVo;
import org.cleverframe.sys.vo.request.UserQueryVo;
import org.cleverframe.sys.vo.request.UserUpdateVo;
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
 * Controller
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:29:05 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/sys/user")
public class UserController extends BaseController {

    @Autowired
    @Qualifier(SysBeanNames.UserService)
    private UserService userService;

    @RequestMapping("/User" + VIEW_PAGE_SUFFIX)
    public ModelAndView getUserJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(SysJspUrlPath.User);
    }

    /**
     * 分页查询
     */
    @RequestMapping("/findByPage")
    @ResponseBody
    public DataGridJson<User> findByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid UserQueryVo userQueryVo,
            BindingResult bindingResult) {
        DataGridJson<User> json = new DataGridJson<>();
        Page<User> page = userService.findByPage(new Page<>(request, response), userQueryVo);
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }

    /**
     * 增加用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public AjaxMessage<String> addUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid UserAddVo userAddVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "新增用户成功", null);
        User user = BeanMapper.mapper(userAddVo, User.class);
        if (beanValidator(bindingResult, message)) {
            if (!userService.addUser(user)) {
                message.setFailMessage("新增用户失败");
            }
        }
        return message;
    }

    /**
     * 更新用户
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public AjaxMessage<String> updateUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid UserUpdateVo userUpdateVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "更新用户成功", null);
        User user = BeanMapper.mapper(userUpdateVo, User.class);
        if (beanValidator(bindingResult, message)) {
            if (!userService.updateUser(user)) {
                message.setFailMessage("更新用户失败");
            }
        }
        return message;
    }

    /**
     * 删除用户(软删除)
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public AjaxMessage<String> deleteUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid UserDeleteVo userDeleteVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "删除用户成功", null);
        User user = BeanMapper.mapper(userDeleteVo, User.class);
        if (beanValidator(bindingResult, message)) {
            if (!userService.deleteUser(user)) {
                message.setFailMessage("删除用户失败");
            }
        }
        return message;
    }
}