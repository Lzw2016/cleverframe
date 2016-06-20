package org.cleverframe.generator.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.vo.response.DataBaseSummaryVo;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schema.View;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
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
}
