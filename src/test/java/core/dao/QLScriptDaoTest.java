package core.dao;

import basetest.DaoTestBase;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.QLScriptDao;
import org.cleverframe.core.entity.QLScript;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-31 9:11 <br/>
 */
@Transactional(readOnly = false)
public class QLScriptDaoTest extends DaoTestBase {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    @Qualifier(CoreBeanNames.QLScriptDao)
    private QLScriptDao qlScriptDao;

    @Test
    public void testGetQLScriptByname() {
        QLScript qlScript = qlScriptDao.getQLScriptByname("test");
        logger.info(qlScript);
    }

    @Test
    public void testFindAllScript() {
        List<QLScript> list = qlScriptDao.findAllScript();
        logger.info(list);
    }

    @Test
    public void testFindQLScriptByPage() {
        Page<QLScript> page = new Page<>(1, 10);
        String name = "";
        String scriptType = "";
        Long id = -1L;
        String uuid = "";
        Character delFlag = QLScript.DEL_FLAG_NORMAL;
        page = qlScriptDao.findQLScriptByPage(page, name, scriptType, id, uuid, delFlag);
        logger.info(page.getList());
    }

    @Test
    public void testDeleteQLScript() {
        boolean flag = qlScriptDao.deleteQLScript("sd");
        logger.info(flag);
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdate() {
        QLScript qLScript = new QLScript();
//        qLScript.setId(1L);
//        qLScript.setName("12345");
        qlScriptDao.getHibernateDao().getSession().update(qLScript);
//        qlScriptDao.getHibernateDao().getSession().flush();
        logger.info(qLScript);
    }
}
