package org.cleverframe.generator.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.service.MateDataService;
import org.cleverframe.generator.vo.response.DataBaseSummaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 18:01 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/generator/matedata")
public class MateDataController extends BaseController {

    @Autowired
    @Qualifier(GeneratorBeanNames.MateDataService)
    private MateDataService mateDataService;

    /**
     * 获取数据库基本信息(概要)<br/>
     * <b>
     * 1.所有数据库名称<br/>
     * 2.数据库得所有表名称<br/>
     * </b>
     */
    // @RequiresRoles("root")
    @RequestMapping("/findAllDataBaseSummary")
    @ResponseBody
    public AjaxMessage<List<DataBaseSummaryVo>> findAllDataBaseSummary(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<DataBaseSummaryVo>> ajaxMessage = new AjaxMessage<>(true, "数据库信息和表信息查询成功", null);
        List<DataBaseSummaryVo> resultList = mateDataService.findAllDataBaseSummary(ajaxMessage);
        ajaxMessage.setResult(resultList);
        return ajaxMessage;
    }
}
