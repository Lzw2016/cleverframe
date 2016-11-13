package org.cleverframe.sys.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 16:34 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/sys/onlineuser")
public class OnlineUserController extends BaseController {

    @Autowired
    @Qualifier(SysBeanNames.OnlineUserService)
    public OnlineUserService onlineUserService;

    @RequestMapping("/getOnlineUser")
    @ResponseBody
    public Object getOnlineUser() {
        return onlineUserService.get();
    }
}
