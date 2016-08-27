package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.service.RedisMonitorService;
import org.cleverframe.monitor.vo.response.RedisMonitorInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 21:32 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/monitor/redis")
public class RedisMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.RedisMonitorService)
    private RedisMonitorService redisMonitorService;

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

//    /**
//     * 获取Redis配置参数的值<br>
//     *
//     * @param parameter 参数名称，可使用“*”匹配，为空返回所有配置
//     */
//    @ResponseBody
//    @RequestMapping("/getConfig")
//    public AjaxMessage getConfig(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            @RequestParam(value = "parameter", required = false) String parameter) {
//        AjaxMessage ajaxMessage = new AjaxMessage();
//        ajaxMessage.setSuccess(true);
//        ajaxMessage.setObject(redisMonitorService.getConfig(parameter));
//        return ajaxMessage;
//    }


//    /**
//     * 查询Redis key值
//     */
//    @ResponseBody
//    @RequestMapping("/getKeys")
//    public AjaxMessage getKeys(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            @RequestParam(value = "pattern") String pattern,
//            @RequestParam(value = "size", required = false, defaultValue = "100") int size) {
//        AjaxMessage ajaxMessage = new AjaxMessage();
//        if (size <= 0) {
//            size = 100;
//        }
//        // pattern 不能是空，或只有“*”
//        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(pattern.replaceAll("\\**", ""))) {
//            ajaxMessage.setSuccess(false);
//            ajaxMessage.setMessage("参数pattern非法！(pattern 不能是空，或只有“*”)");
//            return ajaxMessage;
//        }
//        ajaxMessage.setSuccess(true);
//        ajaxMessage.setObject(redisMonitorService.getKeys(pattern, size));
//        return ajaxMessage;
//    }


//    /**
//     * 获取Key对应的Value，不管其结构
//     *
//     * @param key
//     */
//    @ResponseBody
//    @RequestMapping("/getValue")
//    public AjaxMessage getValue(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            @RequestParam(value = "key") String key) {
//        AjaxMessage ajaxMessage = new AjaxMessage();
//        Object object = redisMonitorService.getValue(key);
//        if (object == null) {
//            ajaxMessage.setSuccess(false);
//            ajaxMessage.setMessage("key不存在！");
//        } else {
//            ajaxMessage.setSuccess(true);
//            ajaxMessage.setObject(object);
//        }
//        return ajaxMessage;
//    }


//    /**
//     * TODO 测试使用
//     */
//    private Thread testThread;
//
//    /**
//     * 随机操作Redis，以便查看监控效果
//     */
//    @ResponseBody
//    @RequestMapping("/startTest")
//    public AjaxMessage startTest(HttpServletRequest request, HttpServletResponse response) {
//        // TODO 测试使用
//        AjaxMessage ajaxMessage = new AjaxMessage();
//        if (testThread != null && testThread.isAlive()) {
//            ajaxMessage.setSuccess(false);
//            ajaxMessage.setMessage("随机操作Redis，已经在运行");
//        } else {
//            testThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    redisMonitorService.test();
//                }
//            });
//            testThread.start();
//            ajaxMessage.setSuccess(true);
//            ajaxMessage.setMessage("随机操作Redis，启动成功");
//        }
//        return ajaxMessage;
//    }
}
