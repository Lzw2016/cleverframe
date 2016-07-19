package org.cleverframe.generator.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.entity.CodeTemplate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 21:58 <br/>
 */
@Repository(GeneratorBeanNames.CodeTemplateDao)
public class CodeTemplateDao extends BaseDao<CodeTemplate> {

    /**
     * 查询所有的代码模版数据
     *
     * @return 所有的代码模版数据
     */
    public List<CodeTemplate> findAllCodeTemplate() {
        Parameter param = new Parameter(CodeTemplate.DEL_FLAG_NORMAL);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.generator.dao.CodeTemplateDao.findAllCodeTemplate");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 代码模版名称是否存在
     *
     * @param name 代码模版名称
     * @return 存在返回true，不存在返回false
     */
    public boolean codeTemplateNameExists(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.generator.dao.CodeTemplateDao.codeTemplateNameExists");
        BigInteger count = hibernateDao.getBySql(null, sql, param);
        return count.longValue() > 0;
    }

    /**
     * 根据代码模版名称获取代码模版(包含软删除了的数据)
     *
     * @param name 代码模版名称
     * @return 代码模版
     */
    public CodeTemplate getByName(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.generator.dao.CodeTemplateDao.getByName");
        return hibernateDao.getBySql(sql, param);
    }

    /**
     * 根据模版名称，直接从数据库删除模版
     *
     * @param name 模版名称
     * @return 删除的数量
     */
    public int delByName(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.generator.dao.CodeTemplateDao.delByName");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate();
    }

    /**
     * 查询一个节点下的子节点数量(不包含自己)
     *
     * @param fullPath 节点路径
     * @return 子节点数量
     */
    public long countByChildNode(String fullPath) {
        fullPath = fullPath + CodeTemplate.FULL_PATH_SPLIT + "%";
        Parameter param = new Parameter(CodeTemplate.DEL_FLAG_NORMAL);
        param.put("fullPath", fullPath);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.generator.dao.CodeTemplateDao.countByChildNode");
        BigInteger count = hibernateDao.getBySql(null, sql, param);
        return count.longValue();
    }

    /**
     * 查询节点的所有子节点，可以排除某个节点和其所有子节点<br/>
     * <b>参数excludeCodeNode==true时,就排除“代码模版”</b>
     *
     * @param fullPath        查询节点路径
     * @param excludePath     排除节点路径
     * @param excludeCodeNode 排除代码模版节点
     * @return 节点集合
     */
    public List<CodeTemplate> findChildNode(String fullPath, String excludePath, boolean excludeCodeNode) {
        Parameter param = new Parameter(CodeTemplate.DEL_FLAG_NORMAL);
        param.put("fullPath", fullPath + "%");
        param.put("excludePath", excludePath +"%");
        if (excludeCodeNode) {
            param.put("excludeCodeNode", "true");
        } else {
            param.put("excludeCodeNode", "");
        }
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.generator.dao.CodeTemplateDao.findChildNode");
        return hibernateDao.findBySql(sql, param);
    }
}
