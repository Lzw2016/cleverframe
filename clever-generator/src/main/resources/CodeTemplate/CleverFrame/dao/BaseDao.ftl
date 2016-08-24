<#assign entityName = "${GeneratorEntityUtils.toClassName(tableSchema.tableName)}">
<#assign daoClassName = "${entityName}Dao">


import org.cleverframe.core.persistence.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO，对应表${tableSchema.tableName}(${tableSchema.description})<br/>
 *
 * 作者：LiZW <br/>
 * 创建时间：${GeneratorEntityUtils.getCurrentTime()?datetime} <br/>
 */
@Repository(BeanNames.${daoClassName})
public class ${daoClassName} extends BaseDao<${entityName}> {
    
}