package org.cleverframe.sys.controller;

import org.apache.shiro.session.Session;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.LoginSession;
import org.cleverframe.sys.service.OnlineUserService;
import org.cleverframe.sys.vo.request.LoginSessionQueryVo;
import org.cleverframe.sys.vo.request.SessionGetVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 16:34 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/sys/onlineuser")
public class OnlineUserController extends BaseController {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(OnlineUserController.class);

    @Autowired
    @Qualifier(SysBeanNames.OnlineUserService)
    public OnlineUserService onlineUserService;

    /**
     * 分页查询在线用户
     */
    @RequestMapping("/findByPage")
    @ResponseBody
    public DataGridJson<LoginSession> findByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid LoginSessionQueryVo loginSessionQueryVo,
            BindingResult bindingResult) {
        DataGridJson<LoginSession> json = new DataGridJson<>();
        Page<LoginSession> page = onlineUserService.findByPage(new Page<>(request, response), loginSessionQueryVo);
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }

    /**
     * 根据sessionId获取Session对象
     */
    @RequestMapping("/getSession")
    @ResponseBody
    public AjaxMessage<Session> getSession(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid SessionGetVo sessionGetVo,
            BindingResult bindingResult) {
        AjaxMessage<Session> message = new AjaxMessage<>(true, "获取Shiro Session数据成功", null);
        if (beanValidator(bindingResult, message)) {
            Session session = null;
            try {
                session = onlineUserService.getSession(sessionGetVo.getSessionId());
            } catch (Exception e) {
                logger.warn("获取Shiro Session失败, SessionId={}", sessionGetVo.getSessionId());
            }
            message.setResult(session);
        }
        return message;
    }


}
