package org.cleverframe.generator.format;

import com.alibaba.druid.sql.SQLUtils;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL格式化。使用Hibernate、OpenJPA、Druid实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 0:07 <br/>
 */
public class SqlFormatUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(SqlFormatUtils.class);

    /**
     * 使用Hibernate格式化SQL
     *
     * @param sql SQL字符串
     * @return 失败返回 ""
     */
    public static String sqlFormatByHibernate(String sql) {
        String result = "";
        try {
            result = FormatStyle.BASIC.getFormatter().format(sql);
        } catch (Throwable e) {
            logger.error("SQL格式化失败[Hibernate]", e);
        }
        return result;
    }

//    /**
//     * 使用OpenJPA格式化SQL
//     *
//     * @param sql SQL字符串
//     * @return 失败返回 ""
//     */
//    public static String sqlFormatByOpenJPA(String sql) {
//        String result = "";
//        try {
//            SQLFormatter formatter = new SQLFormatter();
//            formatter.setLineLength(160);
//            formatter.setMultiLine(false);
//            formatter.setDoubleSpace(true);
//            result = formatter.prettyPrint(sql).toString();
//        } catch (Throwable e) {
//            logger.error("SQL格式化失败[OpenJPA]", e);
//        }
//        return result;
//    }

    /**
     * 使用Druid格式化SQL
     *
     * @param sql    SQL字符串
     * @param dbType 数据库类型，如：JdbcUtils.MYSQL
     * @return 失败返回 ""
     */
    public static String sqlFormatByDruid(String sql, String dbType) {
        String result = "";
        try {
            result = SQLUtils.format(sql, dbType, SQLUtils.DEFAULT_FORMAT_OPTION);
        } catch (Throwable e) {
            logger.error("SQL格式化失败[Druid]", e);
        }
        return result;
    }
}
