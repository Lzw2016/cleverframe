package org.cleverframe.monitor.controller;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.MonitorJspUrlPath;
import org.cleverframe.monitor.service.RedisMonitorService;
import org.cleverframe.monitor.vo.response.RedisMonitorInfoVo;
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
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 21:32 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/monitor/redis")
public class RedisMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.RedisMonitorService)
    private RedisMonitorService redisMonitorService;

    @RequestMapping("/RedisMonitor" + VIEW_PAGE_SUFFIX)
    public ModelAndView getRedisMonitorJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(MonitorJspUrlPath.RedisMonitor);
    }

    /**
     * 获取Redis信息
     */
    @ResponseBody
    @RequestMapping("/getRedisInfo")
    public AjaxMessage<List<RedisMonitorInfoVo>> getRedisInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String section) {
        AjaxMessage<List<RedisMonitorInfoVo>> ajaxMessage = new AjaxMessage<>(true, "获取Redis信息成功", null);
        List<RedisMonitorInfoVo> infoList = redisMonitorService.getRedisInfo(section);
        ajaxMessage.setResult(infoList);
        return ajaxMessage;
    }

    @ResponseBody
    @RequestMapping("/getKeyCount")
    public AjaxMessage<Long> getKeyCount(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<Long> ajaxMessage = new AjaxMessage<>(true, "获取Redis当前数据数量成功", null);
        Long count = redisMonitorService.getKeyCount();
        ajaxMessage.setResult(count);
        return ajaxMessage;
    }

    /**
     * 获取Redis配置参数的值<br>
     *
     * @param parameter 参数名称，可使用“*”匹配，为空返回所有配置
     */
    @ResponseBody
    @RequestMapping("/getConfig")
    public AjaxMessage<List<String>> getConfig(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false, defaultValue = "*") String parameter) {
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "获取Redis配置参数的值成功", null);
        ajaxMessage.setResult(redisMonitorService.getConfig(parameter));
        return ajaxMessage;
    }

    /**
     * 查询Redis key值
     */
    @ResponseBody
    @RequestMapping("/getKeys")
    public AjaxMessage<Set<String>> getKeys(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = true) String pattern,
            @RequestParam(required = false, defaultValue = "100") int size) {
        AjaxMessage<Set<String>> ajaxMessage = new AjaxMessage<>(true, "查询Redis Key成功", null);
        // pattern 不能是空，或只有“*”
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(pattern.replaceAll("\\**", ""))) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("参数pattern非法！(pattern 不能是空，或只有“*”)");
            return ajaxMessage;
        }
        ajaxMessage.setResult(redisMonitorService.getKeys(pattern, size));
        return ajaxMessage;
    }

    /**
     * 获取Key对应的Value，不管其结构
     */
    @ResponseBody
    @RequestMapping("/getValue")
    public AjaxMessage<Object> getValue(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = true) String key) {
        AjaxMessage<Object> ajaxMessage = new AjaxMessage<>(true, "获取Key对应的Value成功", null);
        Object object = redisMonitorService.getValue(key);
        ajaxMessage.setResult(object);
        if (object == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("Key不存在!");
        }
        return ajaxMessage;
    }

    // ----------------------------------------------------------------------------------------------------------------------------

    /**
     * TODO 测试使用,生产环境删除
     */
    private Thread testThread;

    /**
     * 随机操作Redis，以便查看监控效果<br/>
     * TODO 测试使用,生产环境删除
     */
    @RequestMapping("/startTest")
    @ResponseBody
    public AjaxMessage startTest(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage ajaxMessage = new AjaxMessage(true, "随机操作Redis，启动成功", null);
        if (testThread != null && testThread.isAlive()) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("随机操作Redis，已经在运行");
        } else {
            //noinspection Convert2Lambda
            testThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    redisMonitorService.test();
                }
            });
            testThread.start();
        }
        return ajaxMessage;
    }
}
