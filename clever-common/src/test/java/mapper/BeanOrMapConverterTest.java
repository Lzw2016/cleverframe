package mapper;

import model.Class;
import model.Student;
import model.TestModel;
import org.cleverframe.common.mapper.BeanMapConverter;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-30 11:01 <br/>
 */
public class BeanOrMapConverterTest {
    private final static Logger logger = LoggerFactory.getLogger(BeanOrMapConverterTest.class);

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
     * 测试把Map转换成JavaBean对象
     */
    @Test
    public void testToObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 23);
        map.put("height", 1.72);
        map.put("name", "李志伟");
        map.put("sex", true);
        map.put("birthday", new Date());

        TestModel model = new TestModel();
        BeanMapConverter.toObject(model, map);
        logger.info(model.toString());
    }

    /**
     * 测试把把JavaBean对象转换成Map
     */
    @Test
    public void testToMap() {
        TestModel model = new TestModel();
        model.setAge(23);
        model.setHeight(1.72);
        model.setName("李志伟");
        model.setSex(true);
        model.setBirthday(new Date());

        Map<String, Object> map = BeanMapConverter.toMap(model);
        logger.info(map.toString());
    }

    @Test
    public void test001() {
        Student student = new Student();
        student.setAge(23);
        student.setHeight(1.72);
        student.setName("李志伟");
        student.setSex(true);
        student.setBirthday(new Date());

        Class clzz = new Class();
        clzz.setClassName("一班");
        clzz.setGrade(3);
        student.setClzz(clzz);

        Map<String, Object> map = BeanMapConverter.toMap(student);
        logger.info(map.toString());
    }
}
