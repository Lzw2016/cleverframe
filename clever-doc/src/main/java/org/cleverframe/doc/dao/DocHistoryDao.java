package org.cleverframe.doc.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocHistory;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表doc_history(文档历史表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 14:23:37 <br/>
 */
@Repository(DocBeanNames.DocHistoryDao)
public class DocHistoryDao extends BaseDao<DocHistory> {

}