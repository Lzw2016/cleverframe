package org.cleverframe.generator.vo.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 14:37 <br/>
 */
public class ColumnSchemaVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据库名称
     */
    private String schemaName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 序号位置
     */
    private Integer ordinalPosition;

    /**
     * 字符串最大长度，数字精度
     */
    private Integer size;

    /**
     * 字段精度表示
     */
    private String width;

    /**
     * 小数位数
     */
    private Integer decimalDigits;

    /**
     * 所谓Cenerated Column，就是数据库中这一列由其他列计算而得
     */
    private Boolean generated;

    /**
     * 是否是隐藏的列
     */
    private Boolean hidden;

    /**
     * 是否是外键
     */
    private Boolean partOfForeignKey;

    /**
     * 是否建了索引
     */
    private Boolean partOfIndex;

    /**
     * 是否是主键
     */
    private Boolean partOfPrimaryKey;

    /**
     * 是否唯一约束
     */
    private Boolean partOfUniqueIndex;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 是否不能为空
     */
    private Boolean notNull;

    /**
     * 是否自增长
     */
    private Boolean autoIncrement;

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

    /**
     * 列属性
     */
    private Map<String, Object> attributes;
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public Boolean getGenerated() {
        return generated;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getPartOfForeignKey() {
        return partOfForeignKey;
    }

    public void setPartOfForeignKey(Boolean partOfForeignKey) {
        this.partOfForeignKey = partOfForeignKey;
    }

    public Boolean getPartOfIndex() {
        return partOfIndex;
    }

    public void setPartOfIndex(Boolean partOfIndex) {
        this.partOfIndex = partOfIndex;
    }

    public Boolean getPartOfPrimaryKey() {
        return partOfPrimaryKey;
    }

    public void setPartOfPrimaryKey(Boolean partOfPrimaryKey) {
        this.partOfPrimaryKey = partOfPrimaryKey;
    }

    public Boolean getPartOfUniqueIndex() {
        return partOfUniqueIndex;
    }

    public void setPartOfUniqueIndex(Boolean partOfUniqueIndex) {
        this.partOfUniqueIndex = partOfUniqueIndex;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
