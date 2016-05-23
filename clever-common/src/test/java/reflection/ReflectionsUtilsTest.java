package reflection;

import com.google.common.reflect.Reflection;
import model.Student;
import org.junit.*;
import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-22 21:53 <br/>
 */
public class ReflectionsUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(ReflectionsUtilsTest.class);

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
    public void test() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setUrls(ClasspathHelper.forPackage("org.cleverframe"));
        configurationBuilder.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner(), new MethodParameterScanner());

        Reflections reflections = new Reflections(configurationBuilder);

        // 获取所有的类名
//        Set<String> set = reflections.getAllTypes();
//        for (String str : set) {
//            logger.info(str);
//        }

        Student student = new Student();
        student.setName("李志伟");
        student.setAge(23);
        student.setSex(true);
        student.setBirthday(new Date());
        student.setHeight(1.73);
        logger.info(student.toString());

        Set<Method> methodSet = reflections.getMethodsReturn(String.class);
        logger.info(methodSet.toString());

        ReflectionUtils.invokeMethod(null,null);
//        student.getClass().getDeclaredMethod()

    }
}
