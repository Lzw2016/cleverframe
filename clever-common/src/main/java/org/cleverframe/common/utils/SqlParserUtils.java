package org.cleverframe.common.utils;

import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SQL解析工具，处理SQL语句。使用jsqlparser框架<br/>
 * 此工具类参考：Mybatis分页插件 - PageHelper的类com.github.pagehelper.parser.SqlParser<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 20:38 <br/>
 */
public class SqlParserUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(SqlParserUtils.class);

    /**
     * Count(*)列
     */
    private static final List<SelectItem> COUNT_ITEM;
    /**
     * Count(*)表别名
     */
    private static final Alias TABLE_ALIAS;
    /**
     * 缓存已经修改过的sql <br/>
     * TODO 必须使用Ehcahe缓存，确定CACHE的最大数量
     */
    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

    static {
        COUNT_ITEM = new ArrayList<>();
        COUNT_ITEM.add(new SelectExpressionItem(new Column("count(0)")));

        TABLE_ALIAS = new Alias("table_count");
        TABLE_ALIAS.setUseAs(false);
    }

    /**
     * 返回已经生成了的智能Count-sql，以Json序列化的方式返回
     *
     * @return 已经生成了的智能Count-sql，是一个序列化了的 Map&lt;String, String&gt;
     */
    public static String getCountSqlCache() {
        return JSON.toJSONString(CACHE);
    }

    /**
     * 清空已经生成了的智能Count-sql，让其再次重新生成
     */
    public static void clearCountSqlCache() {
        CACHE.clear();
    }

    private static void isSupportedSql(String sql) {
        if (sql.trim().toUpperCase().endsWith("FOR UPDATE")) {
            throw new RuntimeException("分页插件不支持包含for update的sql");
        }
    }

    /**
     * 获取智能的Count-sql
     *
     * @param sql 普通SQL查询语句
     * @return Count-sql
     */
    public static String getSmartCountSql(String sql) {
        // 校验是否支持该sql
        isSupportedSql(sql);
        if (CACHE.get(sql) != null) {
            return CACHE.get(sql);
        }
        // 解析SQL
        Statement stmt;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (Throwable e) {
            logger.warn("获取智能的Count-sql失败，SQL语句：" + sql, e);
            // 无法解析的用一般方法返回count语句
            String countSql = getSimpleCountSql(sql);
            CACHE.put(sql, countSql);
            return countSql;
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        // 处理body-去order by
        processSelectBody(selectBody);
        // 处理with-去order by
        processWithItemsList(select.getWithItemsList());
        // 处理为count查询
        sqlToCount(select);
        String result = select.toString();
        CACHE.put(sql, result);
        return result;
    }

    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    public static String getSimpleCountSql(final String sql) {
        isSupportedSql(sql);
        return "select count(*) from (" + sql + ") tmp_count";
    }

    /**
     * 将sql转换为count查询
     */
    private static void sqlToCount(Select select) {
        SelectBody selectBody = select.getSelectBody();
        // 是否能简化count查询
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(COUNT_ITEM);
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(COUNT_ITEM);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 是否可以用简单的count查询方式
     */
    private static boolean isSimpleCount(PlainSelect select) {
        // 包含group by的时候不可以
        if (select.getGroupByColumnReferences() != null) {
            return false;
        }
        // 包含distinct的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        for (SelectItem item : select.getSelectItems()) {
            // select列中包含参数的时候不可以，否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            // 如果查询列中包含函数，也不可以，函数可能会聚合列
            if (item instanceof SelectExpressionItem) {
                if (((SelectExpressionItem) item).getExpression() instanceof Function) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 处理selectBody去除Order by
     */
    private static void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                List<SelectBody> plainSelects = operationList.getSelects();
                for (SelectBody plainSelect : plainSelects) {
                    processSelectBody(plainSelect);
                }
            }
            if (!orderByHashParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    /**
     * 处理PlainSelect类型的selectBody
     */
    private static void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 处理WithItem
     */
    private static void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            for (WithItem item : withItemsList) {
                processSelectBody(item.getSelectBody());
            }
        }
    }

    /**
     * 处理子查询
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private static void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoin() != null) {
                if (subJoin.getJoin().getRightItem() != null) {
                    processFromItem(subJoin.getJoin().getRightItem());
                }
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
        // Table时不用处理
    }

    /**
     * 判断Orderby是否包含参数，有参数的不能去
     */
    private static boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }
        return false;
    }
}
