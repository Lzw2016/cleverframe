package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.CoreJspUrlPath;
import org.cleverframe.core.entity.AccessLog;
import org.cleverframe.core.service.AccessLogService;
import org.cleverframe.core.vo.request.AccessLogQueryVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
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

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 22:33 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/core/accesslog")
public class AccessLogController extends BaseController {

    @Autowired
    @Qualifier(CoreBeanNames.AccessLogService)
    private AccessLogService accessLogService;

    @RequestMapping("/AccessLog" + VIEW_PAGE_SUFFIX)
    public ModelAndView getAccessLogJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.AccessLog);
    }

    /**
     * 查询配置信息，使用分页
     *
     * @return EasyUI DataGrid控件的json数据
     */
    // @RequiresRoles("root")
    @RequestMapping("/findAccessLogByPage")
    @ResponseBody
    public DataGridJson<AccessLog> findAccessLogByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid AccessLogQueryVo accessLogQueryVo,
            BindingResult bindingResult) {
        DataGridJson<AccessLog> json = new DataGridJson<>();
        Page<AccessLog> qLScriptPage = accessLogService.findByPage(
                new Page<>(request, response),
                accessLogQueryVo.getLoginName(),
                accessLogQueryVo.getRequestStartTime(),
                accessLogQueryVo.getRequestEndTime(),
                accessLogQueryVo.getRequestUri(),
                accessLogQueryVo.getMethod(),
                accessLogQueryVo.getProcessMinTime(),
                accessLogQueryVo.getProcessMaxTime(),
                accessLogQueryVo.getRemoteAddr(),
                accessLogQueryVo.getUserAgent(),
                accessLogQueryVo.getHasException());
        json.setRows(qLScriptPage.getList());
        json.setTotal(qLScriptPage.getCount());
        return json;
    }
}
