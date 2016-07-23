package org.cleverframe.generator.controller;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.GeneratorJspUrlPath;
import org.cleverframe.generator.service.GeneratorCodeService;
import org.cleverframe.generator.service.MateDataService;
import org.cleverframe.generator.vo.model.CodeResultVo;
import org.cleverframe.generator.vo.model.TableSchemaVo;
import org.cleverframe.generator.vo.request.GeneratorCodeVo;
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
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:48 <br/>
 */

@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/generator/generatorcode")
public class GeneratorCodeController extends BaseController {

    @Autowired
    @Qualifier(GeneratorBeanNames.GeneratorCodeService)
    private GeneratorCodeService generatorCodeService;

    @Autowired
    @Qualifier(GeneratorBeanNames.MateDataService)
    private MateDataService mateDataService;

    @RequestMapping("/GeneratorCode" + VIEW_PAGE_SUFFIX)
    public ModelAndView getGeneratorCodeJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(GeneratorJspUrlPath.GeneratorCode);
    }

    /**
     * 根据模版生成代码
     */
    @RequestMapping("/generatorCode")
    @ResponseBody
    public AjaxMessage<CodeResultVo> generatorCode(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid GeneratorCodeVo generatorCodeVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<CodeResultVo> ajaxMessage = new AjaxMessage<>(true, "代码生成成功！", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            // 模版数据-表结构
            TableSchemaVo tableSchema = mateDataService.getTableSchema(generatorCodeVo.getSchemaName(), generatorCodeVo.getTableName(), ajaxMessage);
            if (tableSchema != null) {
                // 模版数据-数据库表中包含的列 List<String>
                JavaType javaType = JacksonMapper.nonEmptyMapper().contructCollectionType(List.class, String.class);
                List<String> includeColumn = JacksonMapper.nonEmptyMapper().fromJson(generatorCodeVo.getIncludeColumn(), javaType);
                // 代码模版集合 CodeResultVo
                CodeResultVo codeTemplate = JacksonMapper.nonEmptyMapper().fromJson(generatorCodeVo.getCodeTemplate(), CodeResultVo.class);
                // 生成代码的一些附加数据 Map<String, String>
                Map<String, String> attributes = null;
                if (StringUtils.isNotBlank(generatorCodeVo.getAttributes())) {
                    javaType = JacksonMapper.nonEmptyMapper().contructMapType(Map.class, String.class, String.class);
                    attributes = JacksonMapper.nonEmptyMapper().fromJson(generatorCodeVo.getAttributes(), javaType);
                }
                // 生成代码
                CodeResultVo codeResultVo = generatorCodeService.generatorCode(tableSchema, includeColumn, codeTemplate, attributes, ajaxMessage);
                ajaxMessage.setResult(codeResultVo);
            }
        }
        return ajaxMessage;
    }
}
