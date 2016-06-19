package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.CoreJspUrlPath;
import org.cleverframe.core.entity.MDict;
import org.cleverframe.core.service.MDictService;
import org.cleverframe.core.vo.request.MDictAddVo;
import org.cleverframe.core.vo.request.MDictDelVo;
import org.cleverframe.core.vo.request.MDictQueryVo;
import org.cleverframe.core.vo.request.MDictUpdateVo;
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
 * 创建时间：2016-6-18 23:08 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping("/${mvcPath}/core/mdict")
public class MDictController extends BaseController {

    @Autowired
    @Qualifier(CoreBeanNames.MDictService)
    private MDictService mDictService;

    @RequestMapping("/MDict" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDictJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.MDict);
    }

    /**
     * 查询，使用分页
     *
     * @return EasyUI DataGrid控件的json数据
     */
    // @RequiresRoles("root")
    @RequestMapping("/findMDictByPage")
    @ResponseBody
    public DataGridJson<MDict> findMDictByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MDictQueryVo mDictQueryVo,
            BindingResult bindingResult) {
        DataGridJson<MDict> json = new DataGridJson<>();
        Page<MDict> qLScriptPage = mDictService.findByPage(
                new Page<MDict>(request, response),
                mDictQueryVo.getMdictKey(),
                mDictQueryVo.getMdictType(),
                mDictQueryVo.getId(),
                mDictQueryVo.getUuid(),
                mDictQueryVo.getDelFlag());
        json.setRows(qLScriptPage.getList());
        json.setTotal(qLScriptPage.getCount());
        return json;
    }

    /**
     * 保存<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/addMDict")
    @ResponseBody
    public AjaxMessage<String> addMDict(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MDictAddVo mDictAddVo,
            BindingResult bindingResult) {
        MDict mDict = BeanMapper.mapper(mDictAddVo, MDict.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            mDictService.saveMDict(mDict, message);
            message.setResult("多级数据字典保存成功");
        }
        return message;
    }

    /**
     * 更新<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/updateMDict")
    @ResponseBody
    public AjaxMessage<String> updateMDict(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MDictUpdateVo mDictUpdateVo,
            BindingResult bindingResult) {
        MDict mDict = BeanMapper.mapper(mDictUpdateVo, MDict.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            mDictService.updateMDict(mDict, message);
            message.setResult("更新多级数据字典成功");
        }
        return message;
    }

    /**
     * 删除<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/deleteMDict")
    @ResponseBody
    public AjaxMessage<String> deleteMDict(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid MDictDelVo mDictDelVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            mDictService.deleteMDict(mDictDelVo.getFullPath());
            message.setResult("多级数据字典删除成功");
        }
        return message;
    }
}
