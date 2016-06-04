package org.cleverframe.core.controller;

import org.cleverframe.common.attributes.CommonApplicationAttributes;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.freemarker.FreeMarkerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全系统共用的JS文件Controller<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-5 0:08 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping("/${mvcPath}/core/globaljs")
public class GlobalJsFileController extends BaseController {

    @RequestMapping("/globalSysPath.js")
    @ResponseBody
    public String getGlobalSysPathJs(HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = request.getServletContext();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put(CommonApplicationAttributes.APP_PATH, servletContext.getAttribute(CommonApplicationAttributes.APP_PATH));
        dataModel.put(CommonApplicationAttributes.STATIC_PATH, servletContext.getAttribute(CommonApplicationAttributes.STATIC_PATH));
        dataModel.put(CommonApplicationAttributes.DOC_PATH, servletContext.getAttribute(CommonApplicationAttributes.DOC_PATH));
        dataModel.put(CommonApplicationAttributes.MODULES_PATH, servletContext.getAttribute(CommonApplicationAttributes.MODULES_PATH));
        dataModel.put(CommonApplicationAttributes.MVC_PATH, servletContext.getAttribute(CommonApplicationAttributes.MVC_PATH));
        return FreeMarkerUtils.templateBindData("test.ftl", dataModel);
    }
}
