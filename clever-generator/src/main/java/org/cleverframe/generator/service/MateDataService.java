package org.cleverframe.generator.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.vo.model.ColumnSchemaVo;
import org.cleverframe.generator.vo.model.DataBaseSummaryVo;
import org.cleverframe.generator.vo.model.TableSchemaVo;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import schemacrawler.schema.*;
import schemacrawler.schemacrawler.*;
import schemacrawler.utility.SchemaCrawlerUtility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 15:38 <br/>
 */
@Service(GeneratorBeanNames.MateDataService)
public class MateDataService extends BaseService {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(MateDataService.class);

    /**
     * Spring提供的Hibernate的模版类
     */
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    @Qualifier(SpringBeanNames.HibernateTemplate)
    private HibernateTemplate hibernateTemplate;

//    /**
//     * 获取数据库连接
//     *
//     * @return 失败返回null
//     */
//    private Connection getConnection() {
//        Connection connection = null;
//        try {
//            connection = SessionFactoryUtils.getDataSource(hibernateTemplate.getSessionFactory()).getConnection();
//        } catch (Throwable e) {
//            logger.error("### 获取数据库连接异常", e);
//        }
//        return connection;
//    }

    /**
     * 获取当前线程的Hibernate的Session
     */
    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    /**
     * 获取数据库基本信息(概要)<br/>
     * <b>
     * 1.所有数据库名称<br/>
     * 2.数据库得所有表名称<br/>
     * </b>
     *
     * @param ajaxMessage 请求响应对象
     * @return 数据库基本信息(概要)
     */
    public List<DataBaseSummaryVo> findAllDataBaseSummary(final AjaxMessage ajaxMessage) {
        final List<DataBaseSummaryVo> resultList = new ArrayList<>();
        Session session = getSession();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
                options.setSchemaInfoLevel(SchemaInfoLevelBuilder.minimum());
                options.setRoutineInclusionRule(new ExcludeAll());
                Catalog catalog;
                try {
                    catalog = SchemaCrawlerUtility.getCatalog(connection, options);
                } catch (SchemaCrawlerException e) {
                    ajaxMessage.setSuccess(false);
                    ajaxMessage.setFailMessage("获取数据库基本信息(概要)出错");
                    logger.error("### findAllDataBaseSummary 获取数据库基本信息(概要)出错", e);
                    return;
                }
                for (final Schema schema : catalog.getSchemas()) {
                    final DataBaseSummaryVo dataBaseSummaryVo = new DataBaseSummaryVo();
                    final List<String> tableNameList = new ArrayList<>();
                    final List<String> viewNames = new ArrayList<>();
                    dataBaseSummaryVo.setTableNameList(tableNameList);
                    dataBaseSummaryVo.setViewNames(viewNames);
                    dataBaseSummaryVo.setSchemaName(schema.getFullName());
                    resultList.add(dataBaseSummaryVo);
                    for (final Table table : catalog.getTables(schema)) {
                        if (table instanceof View) {
                            viewNames.add(table.getName());
                        } else {
                            tableNameList.add(table.getName());
                        }
                    }
                }
            }
        });
        return resultList;
    }

    /**
     * 获取数据库表结构，包含字段(列)的详细信息<br/>
     *
     * @param schemaName  数据库名
     * @param tableName   数据库表名称
     * @param ajaxMessage 请求响应对象
     * @return 数据库表结构信息, 未找到返回null
     */
    public TableSchemaVo getTableSchema(final String schemaName, final String tableName, final AjaxMessage ajaxMessage) {
        final TableSchemaVo tableSchemaVo = new TableSchemaVo();
        if (StringUtils.isBlank(schemaName) || StringUtils.isBlank(tableName)) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("数据库名称和表名称不能为空");
        }
        final String fullTableName = schemaName + "." + tableName;
        Session session = getSession();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
                options.setSchemaInfoLevel(SchemaInfoLevelBuilder.maximum());
                options.setRoutineInclusionRule(new ExcludeAll());
                options.setSchemaInclusionRule(new RegularExpressionInclusionRule(schemaName));
                options.setTableInclusionRule(new RegularExpressionInclusionRule(fullTableName));
                Catalog catalog;
                try {
                    catalog = SchemaCrawlerUtility.getCatalog(connection, options);
                } catch (SchemaCrawlerException e) {
                    ajaxMessage.setSuccess(false);
                    ajaxMessage.setFailMessage("获取数据库基本信息(概要)出错");
                    logger.error("### getTableSchema 获取数据库表结构出错", e);
                    return;
                }
                if (catalog.getTables().size() < 1) {
                    ajaxMessage.setSuccess(false);
                    ajaxMessage.setFailMessage("表不存在，表名称=[" + fullTableName + "]");
                    return;
                }
                if (catalog.getTables().size() > 1) {
                    ajaxMessage.setSuccess(false);
                    ajaxMessage.setFailMessage("存在多个表，表名称=[" + fullTableName + "] 个数=[" + catalog.getTables().size() + "]");
                    return;
                }

                for (final Table table : catalog.getTables()) {
                    List<ColumnSchemaVo> columnList = new ArrayList<>();
                    tableSchemaVo.setColumnList(columnList);
                    tableSchemaVo.setSchemaName(table.getSchema().getFullName());
                    tableSchemaVo.setTableName(table.getName());
                    tableSchemaVo.setDescription(table.getRemarks());
                    for (final Column column : table.getColumns()) {
                        ColumnSchemaVo columnSchemaVo = new ColumnSchemaVo();
                        columnSchemaVo.setSchemaName(table.getSchema().getFullName());
                        columnSchemaVo.setTableName(table.getName());
                        columnSchemaVo.setColumnName(column.getName());
                        columnSchemaVo.setOrdinalPosition(column.getOrdinalPosition());
                        columnSchemaVo.setDataType(column.getColumnDataType().getFullName());
                        columnSchemaVo.setSize(column.getSize());
                        columnSchemaVo.setWidth(column.getWidth());
                        columnSchemaVo.setDecimalDigits(column.getDecimalDigits());
                        columnSchemaVo.setNotNull(column.isNullable());
                        columnSchemaVo.setAutoIncrement(column.isAutoIncremented());
                        columnSchemaVo.setGenerated(column.isGenerated());
                        columnSchemaVo.setHidden(column.isHidden());
                        columnSchemaVo.setPartOfForeignKey(column.isPartOfForeignKey());
                        columnSchemaVo.setPartOfIndex(column.isPartOfIndex());
                        columnSchemaVo.setPartOfPrimaryKey(column.isPartOfPrimaryKey());
                        columnSchemaVo.setPartOfUniqueIndex(column.isPartOfUniqueIndex());
                        columnSchemaVo.setDefaultValue(column.getDefaultValue());
                        columnSchemaVo.setComment(column.getRemarks());
                        columnSchemaVo.setAttributes(column.getAttributes());
                        columnList.add(columnSchemaVo);
                    }
                }
            }
        });
        return tableSchemaVo;
    }
}
