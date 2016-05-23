package utils;

import model.Class;
import model.Student;
import org.cleverframe.common.utils.JavaBeanUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-2 23:44 <br/>
 */
public class JavaBeanUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(JavaBeanUtilsTest.class);

    private static long time;

    /**
     * 所有测试开始之前运行
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        time = System.currentTimeMillis();
    }

    /**
     * 所有测试结束之后运行
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.info("测试用时：" + (System.currentTimeMillis() - time));
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

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testProperty() {
        Student student = new Student();
        student.setName("李志伟");
        student.setAge(23);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.73);

        Class clzz = new Class();
        clzz.setGrade(3);
        clzz.setClassName("五班");
        student.setClzz(clzz);

        String name = JavaBeanUtils.getProperty(student, "name");
        logger.info(name);

        int grade = JavaBeanUtils.getProperty(student, "clzz.grade");
        logger.info(grade + "");

        JavaBeanUtils.setProperty(student, "clzz.grade", 6);
        logger.info(clzz.toString());

        logger.info(JavaBeanUtils.getPropertyType(student, "name").getName());
        logger.info(JavaBeanUtils.getPropertyType(student, "clzz.grade").getName());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testNewObject() {
        Student student = JavaBeanUtils.newObject(Student.class);
        logger.info(student.toString());

        student = JavaBeanUtils.newObject(Student.class, "李志伟", 23);
        logger.info(student.toString());
    }

    @Test
    public void testInvokeMethod() {
        Student student = new Student();
        student.setName("李志伟");
        student.setAge(23);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.73);

        String name = JavaBeanUtils.invokeMethod(student, "getName");
        logger.info(name);

        JavaBeanUtils.invokeMethod(student, "setName", "李志伟XXX");
        logger.info(student.toString());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testClone() {
        Student student = new Student();
        student.setName("李志伟");
        student.setAge(23);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.73);

        Class clzz = new Class();
        clzz.setGrade(3);
        clzz.setClassName("五班");
        student.setClzz(clzz);

        Student studentClone = JavaBeanUtils.clone(student);
        studentClone.setName("XXXXX");
        studentClone.getClzz().setGrade(6);

        logger.info(studentClone.getName());
        logger.info(studentClone.getClzz().getGrade() + "");

        logger.info(student.getName());
        logger.info(student.getClzz().getGrade() + "");
    }

    /**
     * 性能测试
     */
    @Test
    public void testPerformance() {
        for (int i = 0; i < 1000; i++) {
            testClone();
        }
    }
}
