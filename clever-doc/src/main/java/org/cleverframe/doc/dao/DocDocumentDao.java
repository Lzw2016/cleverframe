package org.cleverframe.doc.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocDocument;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表doc_document(文档表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 11:30:31 <br/>
 */
@Repository(DocBeanNames.DocDocumentDao)
public class DocDocumentDao extends BaseDao<DocDocument> {

}