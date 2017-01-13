package org.cleverframe.doc.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.dao.DocHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service，对应表doc_history(文档历史表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-13 14:23:37 <br/>
 */
@Service(DocBeanNames.DocHistoryService)
public class DocHistoryService extends BaseService {

    @Autowired
    @Qualifier(DocBeanNames.DocHistoryDao)
    private DocHistoryDao docHistoryDao;
}