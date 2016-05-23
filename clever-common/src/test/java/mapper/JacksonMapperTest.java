package mapper;

import model.TestModel;
import org.cleverframe.common.mapper.JacksonMapper;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-29 14:39 <br/>
 */
public class JacksonMapperTest {
    private final static Logger logger = LoggerFactory.getLogger(JacksonMapperTest.class);

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
    @SuppressWarnings("unchecked")
    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("String", "字符串");
        map.put("int", 12);
        map.put("long", 13L);
        map.put("double", 13.5);
        String json = JacksonMapper.nonEmptyMapper().toJson(map);
        logger.info(json);
        map.clear();
        map = (Map<String, Object>) JacksonMapper.nonEmptyMapper().fromJson(json, Map.class);
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
        String json = JacksonMapper.nonEmptyMapper().toJson(model);
        logger.info(json);
        TestModel modelCopy = JacksonMapper.nonEmptyMapper().fromJson(json, TestModel.class);
        logger.info(modelCopy.toString());
    }

    /**
     * 测试JavaBean的序列化和反序列化
     */
    @SuppressWarnings({"RedundantCast", "unchecked"})
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

        String json = JacksonMapper.nonEmptyMapper().toJson(modelList);
        logger.info(json);

        modelList.clear();
        JacksonMapper jacksonMapper = JacksonMapper.nonEmptyMapper();
        modelList = (List<TestModel>) jacksonMapper.fromJson(json, jacksonMapper.contructCollectionType(List.class, TestModel.class));
        logger.info(modelList.toString());
    }

    /**
     * 测试JavaBean的序列化和反序列化
     */
    @SuppressWarnings({"unchecked", "RedundantCast"})
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

        String json = JacksonMapper.nonEmptyMapper().toJson(map);
        logger.info(json);

        map.clear();
        JacksonMapper jacksonMapper = JacksonMapper.nonEmptyMapper();
        map = (Map<String, TestModel>) jacksonMapper.fromJson(json, jacksonMapper.contructMapType(Map.class, String.class, TestModel.class));
        logger.info(map.toString());
    }
}
