package org.cleverframe.doc.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocProject;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表doc_project(文档项目表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-12 22:07:44 <br/>
 */
@Repository(DocBeanNames.DocProjectDao)
public class DocProjectDao extends BaseDao<DocProject> {

}