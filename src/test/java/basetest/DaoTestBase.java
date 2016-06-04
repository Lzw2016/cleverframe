package basetest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-26 0:20 <br/>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-all.xml"})
public class DaoTestBase extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * 所有测试开始之前运行
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * 所有测试结束之后运行
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    /**
     * 每一个测试方法之前运行
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * 每一个测试方法之后运行
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        logger.info("### 测试Dao");
    }
}
