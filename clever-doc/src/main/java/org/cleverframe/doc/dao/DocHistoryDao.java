package org.cleverframe.doc.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocHistory;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Set;

/**
 * DAO，对应表doc_history(文档历史表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 14:23:37 <br/>
 */
@Repository(DocBeanNames.DocHistoryDao)
public class DocHistoryDao extends BaseDao<DocHistory> {

    /**
     * 删除文档历史数据
     *
     * @param documentId 文档ID
     * @return 删除数据量
     */
    public int delDocHistory(Serializable documentId) {
        Parameter param = new Parameter();
        param.put("documentId", documentId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocHistoryDao.delDocHistory");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate();
    }

    /**
     * 批量删除文档历史数据
     *
     * @param documentIds 文档ID集合
     * @return 删除数据量
     */
    public int batchDelDocHistory(Set<Serializable> documentIds) {
        Parameter param = new Parameter();
        param.put("documentIds", documentIds);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocHistoryDao.batchDelDocHistory");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate();
    }

    /**
     * 获取某个文档的历史记录 - 支持分页
     *
     * @param documentId 文档ID
     */
    public Page<DocHistory> findByPage(Page<DocHistory> page, Serializable documentId) {
        Parameter param = new Parameter();
        param.put("documentId", documentId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocHistoryDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }
}