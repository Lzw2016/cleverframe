package org.cleverframe.generator.service;

import org.cleverframe.common.freemarker.FreeMarkerUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.utils.GeneratorEntityUtils;
import org.cleverframe.generator.vo.model.CodeResultVo;
import org.cleverframe.generator.vo.model.ColumnSchemaVo;
import org.cleverframe.generator.vo.model.TableSchemaVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:50 <br/>
 */
@Service(GeneratorBeanNames.GeneratorCodeService)
public class GeneratorCodeService extends BaseService {

//    @Autowired
//    @Qualifier(CoreBeanNames.TemplateDao)
//    private TemplateDao templateDao;
//
//    @Autowired
//    @Qualifier(GeneratorBeanNames.CodeTemplateDao)
//    private CodeTemplateDao codeTemplateDao;

    /**
     * 根据模版生成代码
     *
     * @param tableSchema   模版数据-数据库表结构
     * @param includeColumn 模版数据-数据库表中包含的列
     * @param codeTemplate  代码模版
     * @param attributes    生成代码的一些附加数据
     * @return 生成代码集合
     */
    public CodeResultVo generatorCode(TableSchemaVo tableSchema, List<String> includeColumn, CodeResultVo codeTemplate, Map<String, String> attributes) {
        List<ColumnSchemaVo> columnList = new ArrayList<>();
        for (ColumnSchemaVo columnSchemaVo : tableSchema.getColumnList()) {
            for (String columnName : includeColumn) {
                if (columnName.equalsIgnoreCase(columnSchemaVo.getColumnName())) {
                    columnList.add(columnSchemaVo);
                }
            }
        }
        tableSchema.setColumnList(columnList);
        Map<String, Object> data = new HashMap<>();
        data.put("tableSchema", tableSchema);
        data.put("attributes", attributes);
        //
        data.put("GeneratorEntityUtils", new GeneratorEntityUtils());
        String codeResult = FreeMarkerUtils.templateBindDataByTmp(codeTemplate.getCodeContent(), data);

        CodeResultVo codeResultVo = new CodeResultVo();
        codeResultVo.setCodeType(codeTemplate.getCodeType());
        codeResultVo.setTemplateName(codeTemplate.getTemplateName());
        codeResultVo.setCodeContent(codeResult);
        return codeResultVo;
    }
}
