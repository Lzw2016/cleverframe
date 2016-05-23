package mapper;

import model.Class;
import model.Student;
import model.Teacher;
import org.cleverframe.common.mapper.BeanMapper;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-1 12:24 <br/>
 */
public class BeanMapperTest {
    private final static Logger logger = LoggerFactory.getLogger(BeanMapperTest.class);

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
     * 测试不同类型的JavaBean对象转换
     */
    @Test
    public void testMapper() {
        Student student = new Student();
        student.setName("李志伟");
        student.setAge(23);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.73);
        student.setClzz(new Class());

        Teacher teacher = BeanMapper.mapper(student, Teacher.class);
        logger.info(teacher.toString());
    }

    /**
     * 测试不同类型的JavaBean对象转换，基于Collection(集合)的批量转换
     */
    @Test
    public void testMapperCollection() {
        Set<Student> set = new HashSet<>();
        Student student = new Student();
        student.setName("李志伟");
        student.setAge(23);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.73);
        student.setClzz(new Class());
        set.add(student);

        student = new Student();
        student.setName("李志伟2");
        student.setAge(232);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.732);
        student.setClzz(new Class());
        set.add(student);

        List<Teacher> list = BeanMapper.mapperCollection(set, Teacher.class);
        logger.info(list.toString());
    }

    /**
     * 将对象source的值拷贝到对象destinationObject中
     */
    @Test
    public void testCopyTo() {
        Student student = new Student();
        student.setName("李志伟");
        student.setClzz(new Class());

        Teacher teacher = new Teacher();
        teacher.setAge(23);
        teacher.setSex(true);
        teacher.setBirthday(new Date());
        teacher.setHeight(1.73);

        BeanMapper.copyTo(student, teacher);
        logger.info(teacher.toString());
    }
}
