package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.CoreJspUrlPath;
import org.cleverframe.core.entity.Dict;
import org.cleverframe.core.service.DictService;
import org.cleverframe.core.vo.request.DictAddVo;
import org.cleverframe.core.vo.request.DictDelVo;
import org.cleverframe.core.vo.request.DictQueryVo;
import org.cleverframe.core.vo.request.DictUpdateVo;
import org.cleverframe.webui.easyui.data.ComboBoxJson;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 21:26 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/core/dict")
public class DictController extends BaseController {

    @Autowired
    @Qualifier(CoreBeanNames.DictService)
    private DictService dictService;

    @RequestMapping("/Dict" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDictJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Dict);
    }

    /**
     * 查询，使用分页
     *
     * @return EasyUI DataGrid控件的json数据
     */
    // @RequiresRoles("root")
    @RequestMapping("/findDictByPage")
    @ResponseBody
    public DataGridJson<Dict> findDictByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DictQueryVo dictQueryVo,
            BindingResult bindingResult) {
        DataGridJson<Dict> json = new DataGridJson<>();
        Page<Dict> qLScriptPage = dictService.findByPage(
                new Page<Dict>(request, response),
                dictQueryVo.getDictKey(),
                dictQueryVo.getDictType(),
                dictQueryVo.getId(),
                dictQueryVo.getUuid(),
                dictQueryVo.getDelFlag());
        json.setRows(qLScriptPage.getList());
        json.setTotal(qLScriptPage.getCount());
        return json;
    }

    /**
     * 保存<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/addDict")
    @ResponseBody
    public AjaxMessage<String> addDict(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DictAddVo dictAddVo,
            BindingResult bindingResult) {
        Dict dict = BeanMapper.mapper(dictAddVo, Dict.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            dictService.saveDict(dict);
            message.setResult("数据字典保存成功");
        }
        return message;
    }

    /**
     * 更新数据字典对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/updateDict")
    @ResponseBody
    public AjaxMessage<String> updateDict(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DictUpdateVo dictUpdateVo,
            BindingResult bindingResult) {
        Dict dict = BeanMapper.mapper(dictUpdateVo, Dict.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            dictService.updateDict(dict);
            message.setResult("更新数据字典成功");
        }
        return message;
    }

    /**
     * 删除数据字典对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/deleteDict")
    @ResponseBody
    public AjaxMessage<String> deleteDict(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DictDelVo dictDelVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            dictService.deleteDict(dictDelVo.getId());
            message.setResult("数据字典删除成功");
        }
        return message;
    }

    /**
     * 根据字典类型查询所有的字典
     */
    @RequestMapping("/findDictByType")
    @ResponseBody
    public List<ComboBoxJson> findDictByType(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = true, name = "dictType") String dictType) {
        List<ComboBoxJson> comboBoxJsonList = new ArrayList<>();
        List<Dict> dictList = dictService.findDictByType(dictType);
        for (Dict dict : dictList) {
            ComboBoxJson comboBoxJson = new ComboBoxJson(false, dict.getDictKey(), dict.getDictValue(), dict);
            comboBoxJsonList.add(comboBoxJson);
        }
        return comboBoxJsonList;
    }
}
