<#assign entityName = "${GeneratorEntityUtils.toClassName(tableSchema.tableName)}">
<#assign daoClassName = "${entityName}Dao">
<#assign serviceClassName = "${entityName}Service">

import org.cleverframe.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service
 *
 * ���ߣ�LiZW <br/>
 * ����ʱ�䣺${GeneratorEntityUtils.getCurrentTime()?datetime} <br/>
 */
@Service(BeanNames.${serviceClassName})
public class ${serviceClassName} extends BaseService {
    
    @Autowired
    @Qualifier(BeanNames.${daoClassName})
    private ${daoClassName} ${daoClassName?uncap_first};
}
