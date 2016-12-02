package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.MonitorJspUrlPath;
import org.cleverframe.monitor.service.SpringMonitorService;
import org.cleverframe.monitor.vo.response.BeanInfoVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-19 23:30 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/monitor/spring")
public class SpringMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.SpringMonitorService)
    private SpringMonitorService springMonitorService;

    @RequestMapping("/SpringMonitor" + VIEW_PAGE_SUFFIX)
    public ModelAndView getSpringMonitorJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(MonitorJspUrlPath.SpringMonitor);
    }

    /**
     * 获取 Spring Context Bean
     */
    @RequestMapping("/getSpringBeans")
    @ResponseBody
    public DataGridJson<BeanInfoVo> getSpringBeans(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String beanName) {
        DataGridJson<BeanInfoVo> json = new DataGridJson<>();
        List<BeanInfoVo> list = springMonitorService.getSpringBeans(beanName);
        json.setRows(list);
        json.setTotal(list.size());
        return json;
    }

    /**
     * 获取 Spring Web Bean
     */
    @RequestMapping("/getSpringWebBeans")
    @ResponseBody
    public DataGridJson<BeanInfoVo> getSpringWebBeans(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String beanName) {
        DataGridJson<BeanInfoVo> json = new DataGridJson<>();
        List<BeanInfoVo> list = springMonitorService.getSpringWebBeans(beanName);
        json.setRows(list);
        json.setTotal(list.size());
        return json;
    }
}
