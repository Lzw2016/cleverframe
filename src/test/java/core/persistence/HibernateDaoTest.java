package core.persistence;

import basetest.DaoTestBase;
import org.cleverframe.core.entity.QLScript;
import org.cleverframe.core.persistence.dao.HibernateDao;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-15 9:19 <br/>
 */
@Transactional(readOnly = false)
public class HibernateDaoTest extends DaoTestBase {

    private HibernateDao<QLScript> hibernateDao = new HibernateDao<>(QLScript.class);

    @Test
    @Transactional(readOnly = false)
    public void testUpdate() throws Exception {
        QLScript qLScript = new QLScript();
        qLScript.setId(1L);
        qLScript.setRemarks("123456");
        qLScript.setDescription("根据字典类型查询所有字典数据123456");
        hibernateDao.update(qLScript, false, true);
        hibernateDao.flush();
        logger.info("###---");
    }
}
