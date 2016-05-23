package mapper;

import model.TestModel;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.mapper.XStreamMapper;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-29 20:24 <br/>
 */
public class XStreamMapperTest {
    private final static Logger logger = LoggerFactory.getLogger(XStreamMapperTest.class);

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

    /**
     * 测试序列化和反序列化Map
     */
    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("String", "字符串");
        map.put("int", 12);
        map.put("long", 13L);
        map.put("double", 13.5);
        map.put("Date", new Date());
        // getDom4jXStream
        String xml = XStreamMapper.getDom4jXStream().toXml(map);
        logger.info("getDom4jXStream \r\n" + xml);
        map.clear();
        map = XStreamMapper.getXpp3XStream().fromXml(xml);
        logger.info(map.toString());
        logger.info("---------------------------------------------------------------------------------");
        // getDomXStream
        xml = XStreamMapper.getDomXStream().toXml(map);
        logger.info("getDomXStream \r\n" + xml);
        map.clear();
        map = XStreamMapper.getDomXStream().fromXml(xml);
        logger.info(map.toString());
        logger.info("---------------------------------------------------------------------------------");
        // getSaxXStream
        xml = XStreamMapper.getSaxXStream().toXml(map);
        logger.info("getSaxXStream \r\n" + xml);
        map.clear();
        map = XStreamMapper.getSaxXStream().fromXml(xml);
        logger.info(map.toString());
        logger.info("---------------------------------------------------------------------------------");
        // getXpp3XStream
        xml = XStreamMapper.getXpp3XStream().toXml(map);
        logger.info("getXpp3XStream \r\n" + xml);
        map.clear();
        map = XStreamMapper.getXpp3XStream().fromXml(xml);
        logger.info(map.toString());
    }

    /**
     * 测试JavaBean的序列化和反序列化
     */
    @Test
    public void testBean() {
        TestModel model = new TestModel();
        model.setAge(23);
        model.setHeight(1.72);
        model.setName("李志伟");
        model.setSex(true);
        model.setBirthday(new Date());
        String xml = XStreamMapper.getXpp3XStream().toXml(model);
        logger.info("\n" + xml);
        TestModel modelCopy = XStreamMapper.getXpp3XStream().fromXml(xml);
        logger.info(modelCopy.toString());
    }

    /**
     * 测试JavaBean的序列化和反序列化
     */
    @Test
    public void testBeanList() {
        List<TestModel> modelList = new ArrayList<>();
        TestModel model = new TestModel();
        model.setAge(23);
        model.setHeight(1.72);
        model.setName("李志伟");
        model.setSex(true);

        TestModel model2 = new TestModel();
        model2.setAge(24);
        model2.setHeight(1.72);
        model2.setName("李志伟2");
        model2.setSex(true);

        modelList.add(model);
        modelList.add(model2);

        String xml = XStreamMapper.getXpp3XStream().toXml(modelList);
        logger.info("\n" + xml);

        modelList.clear();
        modelList = XStreamMapper.getXpp3XStream().fromXml(xml);
        logger.info(modelList.toString());
    }

    /**
     * 测试JavaBean的序列化和反序列化
     */
    @Test
    public void testBeanMap() {
        Map<String, TestModel> map = new HashMap<>();
        TestModel model = new TestModel();
        model.setAge(23);
        model.setHeight(1.72);
        model.setName("李志伟");
        model.setSex(true);

        TestModel model2 = new TestModel();
        model2.setAge(24);
        model2.setHeight(1.72);
        model2.setName("李志伟2");
        model2.setSex(true);

        map.put("model", model);
        map.put("model2", model2);

        String xml = XStreamMapper.getXpp3XStream().toXml(map);
        logger.info("\n" + xml);

        map.clear();
        map = XStreamMapper.getXpp3XStream().fromXml(xml);
        logger.info(map.toString());
    }

    /**
     * 测试JavaBean的序列化和反序列化
     */
    @Test
    public void testUpdate() {
        TestModel model = new TestModel();
        model.setAge(23);
        model.setHeight(1.72);
        model.setName("李志伟");
        model.setSex(true);
        model.setBirthday(new Date());

        String xml =
                "<model.TestModel>\n" +
                        "  <name>李志伟11111111</name>\n" +
                        "  <sex>false</sex>\n" +
                        "  <birthday>1993-04-29 15:41:16.76 UTC</birthday>\n" +
                        "</model.TestModel>";
        XStreamMapper.getXpp3XStream().update(xml, model);
        logger.info(model.toString());
    }
}
