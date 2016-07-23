package org.cleverframe.generator.utils;

/**
 * 代码生成时使用的工具类
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-23 14:29 <br/>
 */
public class GeneratorEntityUtils {

    /**
     * 根据数据库表名生成类名
     *
     * @param tableName 数据库表名
     * @return 类名
     */
    public String toClassName(String tableName) {
        return "";
    }

    /**
     * 根据数据库列名生成实体类字段名
     *
     * @param columnName 数据库列名
     * @return 实体类字段名
     */
    public String toFieldName(String columnName) {
        return "";
    }

    /**
     * 根据数据库列类型生成实体类字段类型
     *
     * @param dataType 数据库列类型
     * @return 实体类字段类型
     */
    public String toFieldType(String dataType) {
        return "";
    }

    /**
     * 根据实体类字段名生成实体类对应get方法名
     *
     * @param fieldName 实体类字段名
     * @return 实体类对应get方法名
     */
    public String toGetMethodName(String fieldName) {
        return "";
    }

    /**
     * 根据实体类字段名生成实体类对应set方法名
     *
     * @param fieldName 实体类字段名
     * @return 实体类对应set方法名
     */
    public String toSetMethodName(String fieldName) {
        return "";
    }
}
