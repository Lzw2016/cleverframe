package org.cleverframe.core.controller;


import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.CoreJspUrlPath;
import org.cleverframe.core.entity.Config;
import org.cleverframe.core.service.EhCacheConfigService;
import org.cleverframe.core.vo.request.ConfigAddVo;
import org.cleverframe.core.vo.request.ConfigDelVo;
import org.cleverframe.core.vo.request.ConfigQueryVo;
import org.cleverframe.core.vo.request.ConfigUpdateVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 10:45 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/core/config")
@Transactional(readOnly = true)
public class ConfigController extends BaseController {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheConfigService)
    private EhCacheConfigService ehCacheConfigService;

    @RequestMapping("/Config" + VIEW_PAGE_SUFFIX)
    public ModelAndView getDictJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Config);
    }

    /**
     * 查询配置信息，使用分页
     *
     * @return EasyUI DataGrid控件的json数据
     */
    // @RequiresRoles("root")
    @RequestMapping("/findConfigByPage")
    @ResponseBody
    public DataGridJson<Config> findConfigByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ConfigQueryVo configQueryVo,
            BindingResult bindingResult) {
        DataGridJson<Config> json = new DataGridJson<>();
        Page<Config> qLScriptPage = ehCacheConfigService.findByPage(
                new Page<Config>(request, response),
                configQueryVo.getConfigKey(),
                configQueryVo.getConfigValue(),
                configQueryVo.getConfigGroup(),
                configQueryVo.getHotSwap(),
                configQueryVo.getId(),
                configQueryVo.getUuid(),
                configQueryVo.getDelFlag());
        json.setRows(qLScriptPage.getList());
        json.setTotal(qLScriptPage.getCount());
        return json;
    }

    /**
     * 保存配置信息对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/addConfig")
    @ResponseBody
    public AjaxMessage<String> addConfig(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ConfigAddVo configAddVo,
            BindingResult bindingResult) {
        Config config = BeanMapper.mapper(configAddVo, Config.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            ehCacheConfigService.saveConfig(config);
            message.setResult("配置信息保存成功");
        }
        return message;
    }

    /**
     * 更新配置信息对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/updateConfig")
    @ResponseBody
    public AjaxMessage<String> updateConfig(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ConfigUpdateVo configUpdateVo,
            BindingResult bindingResult) {
        Config config = BeanMapper.mapper(configUpdateVo, Config.class);
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            ehCacheConfigService.updateConfig(config);
            message.setResult("更新配置信息对象成功");
        }
        return message;
    }

    /**
     * 删除配置信息对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/deleteConfig")
    @ResponseBody
    public AjaxMessage<String> deleteConfig(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ConfigDelVo configDelVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>();
        if (beanValidator(bindingResult, message)) {
            ehCacheConfigService.deleteConfig(configDelVo.getConfigKey());
            message.setResult("配置信息删除成功");
        }
        return message;
    }
}
