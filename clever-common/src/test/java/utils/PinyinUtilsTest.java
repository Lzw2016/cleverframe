package utils;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.cleverframe.common.pinyin.PinyinUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-1 21:57 <br/>
 */
public class PinyinUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(PinyinUtilsTest.class);

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
    public void testToPinYin() {
        logger.info(PinyinUtils.toPinYin('李'));

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        logger.info(PinyinUtils.toPinYin('李', format));

        logger.info(Arrays.toString(PinyinUtils.toAllPinYin('和')));

        logger.info(PinyinUtils.toPinYin('-'));

        logger.info(PinyinUtils.toHeadPinYin('李') + "");
    }

    @Test
    public void testGetStringPinYin() {
        logger.info(PinyinUtils.getStringPinYin("现代汉语中哪个字的读音最复杂？经查证，这个字是“和”——它的读音多达六种。", "|"));

        logger.info(PinyinUtils.getStringHeadPinYin("现代汉语中哪个字的读音最复杂？经查证，这个字是“和”——它的读音多达六种。", "|"));
    }

    @Test
    public void testIsChineseString() {
        logger.info(PinyinUtils.isChineseString("现代汉语中哪个字的读音最复杂？经查证，这个字是“和”——它的读音多达六种。") + "");

        logger.info(PinyinUtils.isChineseString("现代汉语中哪个字的读音最复杂") + "");
    }
}
