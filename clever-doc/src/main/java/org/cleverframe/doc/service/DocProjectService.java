package org.cleverframe.doc.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.dao.DocDocumentDao;
import org.cleverframe.doc.dao.DocHistoryDao;
import org.cleverframe.doc.dao.DocProjectDao;
import org.cleverframe.doc.entity.DocDocument;
import org.cleverframe.doc.entity.DocProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/12 22:17 <br/>
 */
@Service(DocBeanNames.DocProjectService)
public class DocProjectService extends BaseService {

    @Autowired
    @Qualifier(DocBeanNames.DocProjectDao)
    private DocProjectDao docProjectDao;

    @Autowired
    @Qualifier(DocBeanNames.DocDocumentDao)
    private DocDocumentDao docDocumentDao;

    @Autowired
    @Qualifier(DocBeanNames.DocHistoryDao)
    private DocHistoryDao docHistoryDao;

    /**
     * 新增文档项目
     */
    @Transactional(readOnly = false)
    public boolean addDocProject(DocProject docProject, AjaxMessage ajaxMessage) {
        if (docProjectDao.existsDocProject(docProject.getName())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("文档项目[" + docProject.getName() + "]已经存在");
            return false;
        }
        docProjectDao.getHibernateDao().save(docProject);
        return true;
    }

    /**
     * 更新文档项目
     */
    @Transactional(readOnly = false)
    public boolean updateDocProject(DocProject docProject, AjaxMessage ajaxMessage) {
        DocProject oldDocProject = docProjectDao.getHibernateDao().get(docProject.getId());
        if (oldDocProject == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("更新的文档项目不存在");
            return false;
        }
        if (!oldDocProject.getName().equals(docProject.getName()) && docProjectDao.existsDocProject(docProject.getName())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("文档项目[" + docProject.getName() + "]已经存在");
            return false;
        }
        docProjectDao.getHibernateDao().getSession().evict(oldDocProject);
        docProjectDao.getHibernateDao().update(docProject, false, true);
        return true;
    }

    /**
     * 删除项目文档 - 硬删除所有文档、文档历史
     *
     * @param id 文档项目ID
     */
    @Transactional(readOnly = false)
    public boolean delDocProject(Serializable id, AjaxMessage ajaxMessage) {
        DocProject docProject = docProjectDao.getHibernateDao().get(id);
        if (docProject == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("需要删除的文档项目不存在");
            return false;
        }
        List<DocDocument> docDocumentList = docDocumentDao.findByProjectId(docProject.getId());
        Set<Serializable> documentIds = new HashSet<>();
        for (DocDocument docDocument : docDocumentList) {
            documentIds.add(docDocument.getId());
        }
        docHistoryDao.batchDelDocHistory(documentIds);
        docDocumentDao.delByProjectId(docProject.getId());
        docProjectDao.getHibernateDao().delete(docProject);
        return true;
    }
}
