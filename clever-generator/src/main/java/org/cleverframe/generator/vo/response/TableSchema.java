package org.cleverframe.generator.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 14:26 <br/>
 */
public class TableSchema extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 存储引擎
     */
    private String engine;

    /**
     * 自动增长值
     */
    private Long autoIncrement;

    /**
     * 编码格式
     */
    private String charset;

    /**
     * 排序字符集
     */
    private String collation;

    /**
     * 表注释说明
     */
    private String description;

    /**
     * 表数据总行数
     */
    private Long rowCount;

    /**
     * 数据平均长度
     */
    private Long avgRowLength;

    /**
     * 表数据总长度
     */
    private Long dataLength;

    /**
     * 表创建时间
     */
    private Date createTime;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Long getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    public Long getAvgRowLength() {
        return avgRowLength;
    }

    public void setAvgRowLength(Long avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public Long getDataLength() {
        return dataLength;
    }

    public void setDataLength(Long dataLength) {
        this.dataLength = dataLength;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
