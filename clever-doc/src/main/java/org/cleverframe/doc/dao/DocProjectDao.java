package org.cleverframe.doc.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.entity.DocProject;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * DAO，对应表doc_project(文档项目表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2017-01-12 22:07:44 <br/>
 */
@Repository(DocBeanNames.DocProjectDao)
public class DocProjectDao extends BaseDao<DocProject> {

    /**
     * 分页查询文档项目
     *
     * @param name     项目文档名称
     * @param createBy 创建人
     */
    public Page<DocProject> queryDocProject(Page<DocProject> page, String name, String createBy) {
        Parameter param = new Parameter();
        param.put("name", name);
        param.put("createBy", createBy);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocProjectDao.queryDocProject");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 文档项目是否存在
     *
     * @param name 文档项目名称
     * @return 存在返回true
     */
    public boolean existsDocProject(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.doc.dao.DocProjectDao.existsDocProject");
        BigInteger count = hibernateDao.getBySql(null, sql, param);
        return count.longValue() > 0;
    }
}