package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.MonitorJspUrlPath;
import org.cleverframe.monitor.service.EhCacheMonitorService;
import org.cleverframe.monitor.vo.request.CacheInfoQueryVo;
import org.cleverframe.monitor.vo.request.ElementInfoQueryVo;
import org.cleverframe.monitor.vo.response.CacheInfoVo;
import org.cleverframe.monitor.vo.response.CacheManagerInfoVo;
import org.cleverframe.monitor.vo.response.ElementInfoVo;
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
 * 创建时间：2016-8-17 19:50 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/monitor/ehcache")
public class EhCacheMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.EhCacheMonitorService)
    private EhCacheMonitorService ehCacheMonitorService;

    @RequestMapping("/EhcacheMonitor" + VIEW_PAGE_SUFFIX)
    public ModelAndView getEhcacheMonitorJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(MonitorJspUrlPath.EhcacheMonitor);
    }

    @RequestMapping("/getAllEhCacheNames")
    @ResponseBody
    public AjaxMessage<List<String>> getAllEhCacheNames(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "获取所有的EhCache缓存名称成功", null);
        ajaxMessage.setResult(ehCacheMonitorService.getAllEhCacheNames());
        return ajaxMessage;
    }

    @RequestMapping("/getCacheManagerInfo")
    @ResponseBody
    public AjaxMessage<CacheManagerInfoVo> getCacheManagerInfo(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<CacheManagerInfoVo> ajaxMessage = new AjaxMessage<>(true, "获取缓存管理器信息成功", null);
        ajaxMessage.setResult(ehCacheMonitorService.getCacheManagerInfo());
        return ajaxMessage;
    }

    @RequestMapping("/getCacheInfo")
    @ResponseBody
    public AjaxMessage<CacheInfoVo> getCacheInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CacheInfoQueryVo cacheInfoQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<CacheInfoVo> ajaxMessage = new AjaxMessage<>(true, "获取缓存信息成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            ajaxMessage.setResult(ehCacheMonitorService.getCacheInfo(cacheInfoQueryVo.getCacheName()));
        }
        return ajaxMessage;
    }

    @RequestMapping("/clearCache")
    @ResponseBody
    public AjaxMessage<String> clearCache(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid CacheInfoQueryVo cacheInfoQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "清空缓存数据成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            boolean flag = ehCacheMonitorService.clearCache(cacheInfoQueryVo.getCacheName());
            if (!flag) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("清空缓存数据失败");
            }
        }
        return ajaxMessage;
    }

    @RequestMapping("/getElementInfo")
    @ResponseBody
    public AjaxMessage<ElementInfoVo> getElementInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ElementInfoQueryVo elementInfoQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<ElementInfoVo> ajaxMessage = new AjaxMessage<>(true, "获取缓存元素信息成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            ajaxMessage.setResult(ehCacheMonitorService.getElementInfo(elementInfoQueryVo.getCacheName(), elementInfoQueryVo.getKey()));
        }
        return ajaxMessage;
    }

    @RequestMapping("/removeElement")
    @ResponseBody
    public AjaxMessage<String> removeElement(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ElementInfoQueryVo elementInfoQueryVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "移除缓存元素数据成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            boolean flag = ehCacheMonitorService.removeElement(elementInfoQueryVo.getCacheName(), elementInfoQueryVo.getKey());
            if (!flag) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("移除缓存元素信息失败");
            }
        }
        return ajaxMessage;
    }
}
