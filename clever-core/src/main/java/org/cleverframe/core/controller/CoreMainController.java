package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.core.CoreJspUrlPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-14 17:21 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/core/main")
public class CoreMainController extends BaseController {

    private final RequestMappingHandlerMapping handlerMapping;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public CoreMainController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @RequestMapping("/CoreMain" + VIEW_PAGE_SUFFIX)
    public ModelAndView getCoreMainJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.CoreMain);
    }

    /**
     * 获取所有的Url请求地址
     */
    @RequestMapping("allUrls")
    @ResponseBody
    public Object getAllUrls(HttpServletRequest request, HttpServletResponse response) {
        Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> set = map.keySet();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo requestMappingInfo : set) {
            Set<String> urlArray = requestMappingInfo.getPatternsCondition().getPatterns();
            urlList.addAll(urlArray);
        }
        Collections.sort(urlList);
        return urlList;
    }
}
