package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 20:14 <br/>
 */
public class TableSchemaQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据库名称
     */
    @NotEmpty(message = "数据库名称不能为空")
    private String schemaName;

    /**
     * 表名称
     */
    @NotEmpty(message = "表名称不能为空")
    private String tableName;

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
}
