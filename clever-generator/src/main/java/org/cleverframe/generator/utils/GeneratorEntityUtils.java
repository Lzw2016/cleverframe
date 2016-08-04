package org.cleverframe.generator.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 代码生成时使用的工具类
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-23 14:29 <br/>
 */
public class GeneratorEntityUtils {

    private static final String BYTE = "Byte";

    private static final String CHARACTER = "Character";

    private static final String SHORT = "Short";

    private static final String INTEGER = "Integer";

    private static final String LONG = "Long";

    private static final String FLOAT = "Float";

    private static final String DOUBLE = "Double";

    private static final String BOOLEAN = "Boolean";

    private static final String STRING = "String";

    private static final String DATE = "Date";

    private static final String BIG_DECIMAL = "BigDecimal";

    public static final GeneratorEntityUtils INSTANCE = new GeneratorEntityUtils();

    private GeneratorEntityUtils() {
    }

    /**
     * 生成驼峰样式的名称
     *
     * @param inputString             输入字符串
     * @param firstCharacterUppercase 首字母是否大写
     * @return 驼峰样式的名称
     */
    public String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;
                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }
        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        return sb.toString();
    }

    /**
     * 根据数据库表名生成类名
     *
     * @param tableName 数据库表名
     * @return 类名
     */
    public String toClassName(String tableName) {
        // 除去MySQL转义字符
        tableName = tableName.replace("`", "");
        tableName = tableName.toLowerCase();
        return getCamelCaseString(tableName, true);
    }

    /**
     * 根据数据库列名生成实体类字段名
     *
     * @param columnName 数据库列名
     * @return 实体类字段名
     */
    public String toFieldName(String columnName) {
        // 除去MySQL转义字符
        columnName = columnName.replace("`", "");
        columnName = columnName.toLowerCase();
        return getCamelCaseString(columnName, false);
    }

    /**
     * 根据数据库列类型生成实体类字段类型
     *
     * @param dataType 数据库列类型
     * @return 实体类字段类型
     */
    public String toFieldType(String dataType) {
        if (StringUtils.isBlank(dataType)) {
            return STRING;
        }
        switch (dataType.toUpperCase()) {
            // ------------------------------------------MySQL
            // 数值类型
            case "TINYINT":
                return SHORT;
            case "SMALLINT":
            case "MEDIUMINT":
            case "INT":
            case "INTEGER":
                return INTEGER;
            case "BIGINT":
                return LONG;
            case "FLOAT":
                return FLOAT;
            case "DOUBLE":
                return DOUBLE;
            case "DECIMAL":
                return BIG_DECIMAL;
            // 日期和时间类型
            case "DATE":
            case "TIME":
            case "YEAR":
            case "DATETIME":
            case "TIMESTAMP":
                return DATE;
            // 字符串类型
            case "CHAR":
                return CHARACTER;
            case "VARCHAR":
            case "TINYTEXT":
            case "TEXT":
            case "MEDIUMTEXT":
            case "LONGTEXT":
                return STRING;
            case "TINYBLOB":
            case "BLOB":
            case "MEDIUMBLOB":
            case "LONGBLOB":
                return BYTE;
        }
        return STRING;
    }

    /**
     * 根据实体类字段名生成实体类对应get方法名<br/>
     * <pre>
     * eMail > geteMail()
     * firstName > getFirstName()
     * URL > getURL()
     * XAxis > getXAxis()
     * a > getA()
     * B > 无效 - 不支持这种情况
     * Yaxis > 无效 - 不支持这种情况
     *
     * private String getepath    -->    getGetepath()
     * private String getEpath    -->    getGetEpath()
     * private String epath       -->    getEpath()
     * private String ePath       -->    getePath()    // 首字母不用大写
     * private String Epath       -->    getEpath()    // 和epath的getter方法是一样的
     * private String EPath       -->    getEPath()*
     * private boolean isenable   -->    isIsenable()
     * private boolean isEnable   -->    isEnable()    // 不是把首字母大写并在前面加is，其结果和enable的getter方法相同
     * private boolean enable     -->    isEnable()
     * private boolean eNable     -->    iseNable()    // 首字母不用大写
     * private boolean Enable     -->    isEnable()    // 和enable的getter方法相同
     * private boolean ENable     -->    isENable()    //
     * </pre>
     *
     * @param fieldName 实体类字段名
     * @param dataType  数据库列类型
     * @return 实体类对应get方法名
     */
    public String toGetMethodName(String fieldName, String dataType) {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        // 如果类型是Boolean类型使用"is"前缀
        if (BOOLEAN.equalsIgnoreCase(toFieldType(dataType))) {
            sb.insert(0, "is"); //$NON-NLS-1$
        } else {
            sb.insert(0, "get"); //$NON-NLS-1$
        }
        return sb.toString();
    }

    /**
     * 根据实体类字段名生成实体类对应set方法名
     * <pre>
     * eMail > seteMail()
     * firstName > setFirstName()
     * URL > setURL()
     * XAxis > setXAxis()
     * a > setA()
     * B > 无效 - 不支持这种情况
     * Yaxis > 无效 - 不支持这种情况
     * </pre>
     *
     * @param fieldName 实体类字段名
     * @return 实体类对应set方法名
     */
    public String toSetMethodName(String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        sb.insert(0, "set"); //$NON-NLS-1$
        return sb.toString();
    }

    /**
     * 获取当前时间
     */
    public Date getCurrentTime() {
        return new Date();
    }
}
