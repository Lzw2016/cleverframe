package org.cleverframe.doc.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.dao.DocDocumentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service，对应表doc_document(文档表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 11:30:32 <br/>
 */
@Service(DocBeanNames.DocDocumentService)
public class DocDocumentService extends BaseService {

    @Autowired
    @Qualifier(DocBeanNames.DocDocumentDao)
    private DocDocumentDao docDocumentDao;
}
