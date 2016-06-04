package org.cleverframe.core.controller;

import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.CoreJspUrlPath;
import org.cleverframe.core.entity.QLScript;
import org.cleverframe.core.qlscript.IQLScriptService;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.core.vo.request.QLScriptAddVo;
import org.cleverframe.core.vo.request.QLScriptQueryVo;
import org.cleverframe.core.vo.request.QLScriptUpdateVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-29 11:49 <br/>
 * TODO 重构修改
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping("/${mvcPath}/core/qlscript")
public class QLScriptController extends BaseController {

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheQLScriptService)
    private IQLScriptService qLScriptService;

    /**
     * 跳转到数据库脚本管理页面<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/getQLScriptJsp")
    public ModelAndView getQLScriptJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.QLScript);
    }

    /**
     * 查询数据库脚本，使用分页
     *
     * @return EasyUI DataGrid控件的json数据
     */
    // @RequiresRoles("root")
    @RequestMapping("/findQLScriptByPage")
    public DataGridJson<QLScript> findQLScriptByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid QLScriptQueryVo qlScriptQueryVo,
            BindingResult bindingResult) {
        DataGridJson<QLScript> json = new DataGridJson<>();
        Page<QLScript> qLScriptPage = qLScriptService.findAllQLScript(
                new Page<QLScript>(request, response),
                qlScriptQueryVo.getName(),
                qlScriptQueryVo.getScriptType(),
                qlScriptQueryVo.getId(),
                qlScriptQueryVo.getUuid(),
                qlScriptQueryVo.getDelFlag());
        json.setRows(qLScriptPage.getList());
        json.setTotal(qLScriptPage.getCount());
        return json;
    }

    /**
     * 保存数据库脚本对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/addQLScript")
    @ResponseBody
    public AjaxMessage<String> addQLScript(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid QLScriptAddVo qlScriptAddVo,
            BindingResult bindingResult) {
//        String qlStr = EncodeDecodeUtils.unescapeHtml(qlScriptAddVo.getScript());// HTML转码
//        qlScriptAddVo.setScript(qlStr);
//        QLScript qLScript = BeanMapper.mapper(qlScriptAddVo, QLScript.class);
//        AjaxMessage<String> message = new AjaxMessage<>();
//        if (beanValidator(bindingResult, message)) {
//            qLScriptService.saveQLScript(qLScript);
//            message.setResult("数据库脚本保存成功");
//        }
        AjaxMessage<String> message =  new AjaxMessage<>("李志伟","操作成功");
        return message;
    }

    /**
     * 更新数据库脚本对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/updateQLScript")
    public ModelAndView updateQLScript(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid QLScriptUpdateVo qlScriptUpdateVo,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();


        String qlStr = EncodeDecodeUtils.unescapeHtml(qlScriptUpdateVo.getScript());// HTML转码
        qlScriptUpdateVo.setScript(qlStr);
        QLScript qLScript = BeanMapper.mapper(qlScriptUpdateVo, QLScript.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            qLScriptService.updateQLScript(qLScript);
            QLScriptUtils.removeTemplateCache(qLScript.getName());
            message.setResult("数据库脚本保存成功");
        }
//        AjaxMessage<String> message =  new AjaxMessage<>("李志伟","操作成功");
        modelAndView.getModelMap().put(XML_OR_JSON_ROOT, message);
        return modelAndView;

    }

    /**
     * 删除数据库脚本对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/deleteQLScript")
    public AjaxMessage<String> deleteQLScript(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name", required = true) String name) {
        qLScriptService.deleteQLScript(name);
        QLScriptUtils.removeTemplateCache(name);
        return new AjaxMessage<>(true, "删除成功", "");
    }
}
