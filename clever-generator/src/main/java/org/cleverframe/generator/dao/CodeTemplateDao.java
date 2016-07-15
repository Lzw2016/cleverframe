package org.cleverframe.generator.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.entity.CodeTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 21:58 <br/>
 */
@Repository(GeneratorBeanNames.CodeTemplateDao)
public class CodeTemplateDao extends BaseDao<CodeTemplate> {

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
}
