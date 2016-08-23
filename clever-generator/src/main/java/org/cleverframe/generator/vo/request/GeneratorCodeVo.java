package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 生成代码请求数据封装
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:20 <br/>
 */
public class GeneratorCodeVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 模版数据-数据库名
     */
    @NotNull(message = "数据库名不能为空")
    private String schemaName;

    /**
     * 模版数据-数据库表名称
     */
    @NotNull(message = "数据库表名称不能为空")
    private String tableName;

    /**
     * 模版数据-数据库表中包含的列 List<String>
     */
    @NotNull(message = "数据库表中包含的列不能为空")
    private String includeColumn;

    /**
     * 代码模版 CodeResultVo
     */
    @NotNull(message = "代码模版不能为空")
    private String codeTemplate;

    /**
     * 生成代码的一些附加数据 Map<String, String>
     */
    private String attributes;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIncludeColumn() {
        return includeColumn;
    }

    public void setIncludeColumn(String includeColumn) {
        this.includeColumn = includeColumn;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
