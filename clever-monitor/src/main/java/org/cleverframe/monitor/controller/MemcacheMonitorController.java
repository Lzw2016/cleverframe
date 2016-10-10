package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.MonitorJspUrlPath;
import org.cleverframe.monitor.service.MemcacheMonitorService;
import org.cleverframe.monitor.vo.response.MemcachedMonitorStatVo;
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
 * 创建时间：2016-8-28 17:06 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/monitor/memcache")
public class MemcacheMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.MemcacheMonitorService)
    private MemcacheMonitorService memcacheMonitorService;

    @RequestMapping("/MemcacheMonitor" + VIEW_PAGE_SUFFIX)
    public ModelAndView getMemcacheMonitorJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(MonitorJspUrlPath.MemcacheMonitor);
    }

    /**
     * 获取Memcached 统计信息<br>
     */
    @ResponseBody
    @RequestMapping("/getStats")
    public AjaxMessage<List<MemcachedMonitorStatVo>> getStats(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = true) String ipAddress,
            @RequestParam(required = true) int port) {
        AjaxMessage<List<MemcachedMonitorStatVo>> ajaxMessage = new AjaxMessage<>(true, "获取Memcached统计信息成功", null);
        List<MemcachedMonitorStatVo> statList = memcacheMonitorService.getStats(ipAddress, port);
        ajaxMessage.setResult(statList);
        if (statList == null || statList.size() <= 0) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("Memcached服务器IP地址端口号参数不正确：[" + ipAddress + ":" + port + "]");
        }
        return ajaxMessage;
    }

    /**
     * TODO 测试使用,生产环境删除
     */
    private Thread testThread;

    /**
     * 随机操作Memcached，以便查看监控效果<br/>
     * TODO 测试使用,生产环境删除
     */
    @ResponseBody
    @RequestMapping("/startTest")
    public AjaxMessage startTest(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage ajaxMessage = new AjaxMessage(true, "随机操作Memcached，启动成功", null);
        if (testThread != null && testThread.isAlive()) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("随机操作Memcached，已经在运行");
        } else {
            //noinspection Convert2Lambda
            testThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    memcacheMonitorService.test();
                }
            });
            testThread.start();
        }
        return ajaxMessage;
    }
}
