package example;

import org.apache.commons.io.FileUtils;
import org.cleverframe.common.mapper.JacksonMapper;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-12 9:52 <br/>
 */
public class TmpTest {
    private final static Logger logger = LoggerFactory.getLogger(TmpTest.class);

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
    public void test() throws IOException {
        String[] mccCodeArray = {"5812",
                "5399",
                "5331",
                "5311",
                "5411",
                "5462",
                "4812",
                "5611",
                "5699",
                "5499",
                "5422",
                "5722",
                "5641",
                "5945",
                "5944",
                "5712",
                "5719",
                "5912",
                "5511",
                "5541",
                "5943",
                "4121",
                "7995",
                "7230",
                "8043",
                "5947",
                "5970",
                "7299",
                "5992",
                "4722",
                "7011",
                "7622",
                "7221",
                "5466",
                "5942",
                "7932",
                "7994",
                "5940",
                "5941",
                "5995",
                "5735",
                "5813",
                "5993",
                "5733",
                "5971",
                "5467",
                "7298",
                "5914",
                "5984"};
        String str = FileUtils.readFileToString(new File("E:\\Source\\HBuilderProject\\Test\\test01\\industryData.json"));
        industryData[] array = JacksonMapper.nonEmptyMapper().fromJson(str,industryData[].class);
        logger.info(array.length + "");

        for(industryData tmp : array) {
            for(String code : mccCodeArray) {
                if(code.equals(tmp.getMccCode())) {
                    tmp.setProdCode("P2016032369BE5D");
                    break;
                }
            }
        }

        str = JacksonMapper.nonEmptyMapper().toJson(array);
        logger.info("---------------------------------------------------------------------------------");
        logger.info(str);

    }
}
