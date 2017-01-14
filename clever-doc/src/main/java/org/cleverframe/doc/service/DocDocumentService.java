package org.cleverframe.doc.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.dao.DocDocumentDao;
import org.cleverframe.doc.dao.DocHistoryDao;
import org.cleverframe.doc.entity.DocDocument;
import org.cleverframe.doc.entity.DocHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Autowired
    @Qualifier(DocBeanNames.DocHistoryDao)
    private DocHistoryDao docHistoryDao;

    /**
     * 获取项目所有的文档信息，不包含文档内容
     *
     * @param projectId 项目ID
     */
    public List<DocDocument> findByProjectId(Serializable projectId) {
        return docDocumentDao.findByProjectId(projectId);
    }

    /**
     * 获取文档信息 - 包含文档内容
     */
    public DocDocument getDocDocument(Serializable id) {
        return docDocumentDao.getHibernateDao().get(id);
    }

    /**
     * 新增文档
     */
    @Transactional(readOnly = false)
    public boolean addDocDocument(DocDocument docDocument, AjaxMessage ajaxMessage) {
        DocDocument parentDocDocument = null;
        if (docDocument.getParentId() != -1) {
            parentDocDocument = docDocumentDao.getHibernateDao().get(docDocument.getParentId());
            if (parentDocDocument == null || !parentDocDocument.getProjectId().equals(docDocument.getProjectId())) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("新增文档失败-父文档不存在");
                return false;
            }
        }
        docDocument.setFullPath("-1");
        docDocumentDao.getHibernateDao().save(docDocument);
        if (parentDocDocument == null) {
            docDocument.setFullPath(docDocument.getId().toString());
        } else {
            docDocument.setFullPath(parentDocDocument.getFullPath() + DocDocument.FULL_PATH_SPLIT + docDocument.getId());
        }
        docDocumentDao.getHibernateDao().update(docDocument);
        return true;
    }


    /**
     * 更新文档
     */
    @Transactional(readOnly = false)
    public boolean updateDocDocument(DocDocument docDocument, AjaxMessage ajaxMessage) {
        DocDocument oldDocDocument = docDocumentDao.getHibernateDao().get(docDocument.getId());
        if (oldDocDocument == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("更新的文档不存在");
            return false;
        }
        // 修改了父节点 - 修改 FullPath
        if (!oldDocDocument.getParentId().equals(docDocument.getParentId())) {
            if (docDocument.getParentId() == -1) {
                docDocument.setFullPath(docDocument.getId().toString());
            } else {
                DocDocument parentDocDocument = docDocumentDao.getHibernateDao().get(docDocument.getParentId());
                if (parentDocDocument == null || !parentDocDocument.getProjectId().equals(oldDocDocument.getProjectId())) {
                    ajaxMessage.setSuccess(false);
                    ajaxMessage.setFailMessage("更新文档失败-父文档不存在");
                    return false;
                } else {
                    docDocument.setFullPath(parentDocDocument.getFullPath() + DocDocument.FULL_PATH_SPLIT + docDocument.getId());
                }
            }
        }
        // 新增历史记录
        String oldContent = StringUtils.trimToEmpty(oldDocDocument.getContent());
        String content = StringUtils.trimToEmpty(docDocument.getContent());
        if (!oldContent.equals(content)) {
            DocHistory docHistory = new DocHistory();
            docHistory.setCreateDate(new Date());
            docHistory.setCreateBy(getUserUtils().getUserCode());
            docHistory.setDocumentId(docDocument.getId());
            docHistory.setContent(docDocument.getContent());
            docHistoryDao.getHibernateDao().save(docHistory);
        }
        docDocumentDao.getHibernateDao().getSession().evict(oldDocDocument);
        docDocumentDao.getHibernateDao().update(docDocument, false, true);
        return true;
    }

    /**
     * 删除文档 - 数据库硬删除，删除历史
     */
    @Transactional(readOnly = false)
    public boolean delDocDocument(DocDocument docDocument, AjaxMessage ajaxMessage) {
        if (docDocumentDao.getChildCount(docDocument.getId()) > 0) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("删除文档失败-存在子文档不能删除");
            return false;
        }
        docDocument = docDocumentDao.getHibernateDao().get(docDocument.getId());
        if (docDocument == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("删除文档失败-删除的文档不存在");
            return false;
        }
        docDocumentDao.getHibernateDao().delete(docDocument);
        docHistoryDao.delDocHistory(docDocument.getId());
        return true;
    }

    /**
     * 还原文档内容
     *
     * @param documentId 文档ID
     * @param historyId  文档历史ID
     */
    @Transactional(readOnly = false)
    public boolean revertDocDocument(Serializable documentId, Serializable historyId, AjaxMessage ajaxMessage) {
        DocDocument docDocument = docDocumentDao.getHibernateDao().get(documentId);
        if (docDocument == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("文档内容还原失败-文档不存在");
            return false;
        }
        DocHistory docHistory = docHistoryDao.getHibernateDao().get(historyId);
        if (docHistory == null || !docHistory.getDocumentId().equals(documentId)) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("文档内容还原失败-历史文档不存在");
            return false;
        }
        docDocument.setContent(docHistory.getContent());
        docDocumentDao.getHibernateDao().update(docDocument);
        return true;
    }
}
