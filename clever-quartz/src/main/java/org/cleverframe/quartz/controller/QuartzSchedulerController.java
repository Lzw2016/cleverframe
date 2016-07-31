package org.cleverframe.quartz.controller;

import org.cleverframe.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-30 12:38 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/quartz/scheduler")
public class QuartzSchedulerController extends BaseController {
}
