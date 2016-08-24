<#assign entityName = "${GeneratorEntityUtils.toClassName(tableSchema.tableName)}">
<#assign serviceClassName = "${entityName}Service">
<#assign controllerClassName = "${entityName}Controller">

import org.cleverframe.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller
 *
 * 作者：LiZW <br/>
 * 创建时间：${GeneratorEntityUtils.getCurrentTime()?datetime} <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${r'${mvcPath}'}/xxxx/${entityName?lower_case}")
public class ${controllerClassName} extends BaseController {
    
    @Autowired
    @Qualifier(BeanNames.${serviceClassName})
    private ${serviceClassName} ${serviceClassName?uncap_first};
    
}
