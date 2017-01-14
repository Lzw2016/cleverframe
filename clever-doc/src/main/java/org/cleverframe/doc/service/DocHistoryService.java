package org.cleverframe.doc.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.dao.DocHistoryDao;
import org.cleverframe.doc.entity.DocHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;

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

    /**
     * 获取某个文档的历史记录 - 支持分页
     *
     * @param documentId 文档ID
     */
    public Page<DocHistory> findByPage(Page<DocHistory> page, Serializable documentId) {
        return docHistoryDao.findByPage(page, documentId);
    }
}