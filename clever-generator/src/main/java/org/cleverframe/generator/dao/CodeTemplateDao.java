package org.cleverframe.generator.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.entity.CodeTemplate;
import org.springframework.stereotype.Repository;

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
        long count = hibernateDao.getBySql(long.class, sql, param);
        return count > 0;
    }


}
