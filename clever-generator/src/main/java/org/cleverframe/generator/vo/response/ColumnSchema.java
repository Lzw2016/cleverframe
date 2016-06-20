package org.cleverframe.generator.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 14:37 <br/>
 */
public class ColumnSchema extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 是否不能为空
     */
    private boolean notNull;

    /**
     * 是否自增长
     */
    private boolean autoIncrement;

    /**
     * 键约束(主键、外键、唯一约束、索引)
     */
    private String key;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 扩展属性
     */
    private String extra;

    /**
     * 编码格式
     */
    private String charset;

    /**
     * 排序字符集
     */
    private String collation;

    /**
     * 列注释
     */
    private String comment;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
