package org.cleverframe.generator.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

import java.util.List;

/**
 * 数据库信息概要，详细信息使用类{@link DataBaseSchemaVo}<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 17:35 <br/>
 */
public class DataBaseSummaryVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据库名称
     */
    private String schemaName;

    /**
     * 数据库表名称
     */
    private List<String> tableNameList;

    /**
     * 数据库视图名称
     */
    private List<String> viewNames;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public List<String> getTableNameList() {
        return tableNameList;
    }

    public void setTableNameList(List<String> tableNameList) {
        this.tableNameList = tableNameList;
    }

    public List<String> getViewNames() {
        return viewNames;
    }

    public void setViewNames(List<String> viewNames) {
        this.viewNames = viewNames;
    }
}
