package freemarker;

import org.cleverframe.common.freemarker.FreeMarkerUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-25 11:30 <br/>
 */
public class FreeMarkerUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(FreeMarkerUtilsTest.class);

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
    public void testGetTemplateStr() {

    }

    @Test
    public void templateBindData() {

    }

    @Test
    public void templateBindDataByTmp() {
        String templateStr = "欢迎：${name}";
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", "李志伟");
        String result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
        logger.info(result);

        templateStr = "欢迎：${user}";
        dataModel.put("user", "李志伟123456");
        result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
        logger.info(result);

        templateStr = "欢迎：${username!\"李志伟789\"}";
        result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
        logger.info(result);

        templateStr = "欢迎：${username}";
        result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
        logger.info(result);
    }
}
