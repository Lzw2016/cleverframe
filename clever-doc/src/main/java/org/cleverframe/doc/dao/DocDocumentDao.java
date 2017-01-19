package org.cleverframe.doc.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocDocument;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * DAO，对应表doc_document(文档表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 11:30:31 <br/>
 */
@Repository(DocBeanNames.DocDocumentDao)
public class DocDocumentDao extends BaseDao<DocDocument> {

    /**
     * 获取子文档数量
     *
     * @param id 文档ID
     */
    public long getChildCount(Serializable id) {
        Parameter param = new Parameter();
        param.put("id", id);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocDocumentDao.getChildCount");
        BigInteger count = hibernateDao.getBySql(null, sql, param);
        return count.longValue();
    }

    /**
     * 获取项目所有的文档信息，不包含文档内容
     *
     * @param projectId   项目ID
     * @param fullPath    匹配的路径
     * @param excludePath 不匹配的路径
     */
    public List<DocDocument> findByProjectId(Serializable projectId, String fullPath, String excludePath) {
        Parameter param = new Parameter();
        param.put("projectId", projectId);
        param.put("fullPath", fullPath);
        param.put("excludePath", excludePath);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocDocumentDao.findByProjectId");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 删除一个项目的所有文档
     *
     * @param projectId 项目ID
     */
    public int delByProjectId(Serializable projectId) {
        Parameter param = new Parameter();
        param.put("projectId", projectId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocDocumentDao.delByProjectId");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate();
    }
}