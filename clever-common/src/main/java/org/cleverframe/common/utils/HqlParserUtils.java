package org.cleverframe.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HQL解析工具，获取Count-HQL<br/>
 * TODO 需要改进
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 21:27 <br/>
 */
public class HqlParserUtils {
    /**
     * 将hql查询转换为count查询，需要改进
     *
     * @return 转换后的HQL字符串
     */
    public static String hqlToCount(String hqlQuery) {
        if (hqlQuery != null && StringUtils.isNotBlank(hqlQuery)) {
            hqlQuery = "select count(*) " + removeSelect(removeOrders(hqlQuery));
        }
        return hqlQuery;
    }

    /**
     * 去除HQL的select子句。
     */
    private static String removeSelect(String qlString) {
        int beginPos = qlString.toLowerCase().indexOf("from");
        return qlString.substring(beginPos);
    }

    /**
     * 去除HQL的orderBy子句。
     */
    private static String removeOrders(String qlString) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(qlString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
