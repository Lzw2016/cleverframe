package org.cleverframe.core.controller;


import org.cleverframe.common.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 10:45 <br/>
 */
@Controller
@RequestMapping(value = "/${mvcPath}/core/config")
@Transactional(readOnly = true)
public class ConfigController extends BaseController {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @RequestMapping("/index")
    public ModelAndView getDictJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object> test(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("result1","value1");
        map.put("result2","value2");
        map.put("result3","value3");
        map.put("result4","value4");
        return map;
    }
}
