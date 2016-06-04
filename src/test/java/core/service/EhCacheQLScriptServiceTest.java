package core.service;

import basetest.ServiceTestBase;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.QLScript;
import org.cleverframe.core.service.EhCacheQLScriptService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-31 16:58 <br/>
 */
@Transactional(readOnly = false)
public class EhCacheQLScriptServiceTest extends ServiceTestBase {

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheQLScriptService)
    private EhCacheQLScriptService ehCacheQLScriptService;

    @Test
    @Transactional(readOnly = false)
    public void testUpdateQLScript(){
        logger.info(ehCacheQLScriptService.findAllQLScript());

        QLScript qLScript = new QLScript();
        qLScript.setId(1L);
        qLScript.setName("12345");
        boolean flag = ehCacheQLScriptService.updateQLScript(qLScript);
        logger.info(flag);
    }
}
