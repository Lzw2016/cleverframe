package codec;

import org.cleverframe.common.codec.CryptoUtils;
import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-7 16:41 <br/>
 */
public class CryptoUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(CryptoUtilsTest.class);

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
    public void testGenerateKey() {
        byte[] key = CryptoUtils.generateHmacSha1Key();
        logger.info(EncodeDecodeUtils.encodeHex(key));

        key = CryptoUtils.generateAesKey();
        logger.info(EncodeDecodeUtils.encodeHex(key));

        key = CryptoUtils.generateAesKey(256);
        logger.info(EncodeDecodeUtils.encodeHex(key));
    }

    @Test
    public void testHmacSha1() {
        byte[] key = CryptoUtils.generateHmacSha1Key();
        logger.info(EncodeDecodeUtils.encodeHex(key));

        byte[] input = "李志伟".getBytes();
        byte[] data = CryptoUtils.hmacSha1(input, key);
        logger.info(EncodeDecodeUtils.encodeHex(data));

        boolean flag = CryptoUtils.isHmacSha1Valid(data, input, key);
        logger.info(flag + "");
    }

    @Test
    public void testAES() {
        byte[] key = CryptoUtils.generateAesKey();
        logger.info(EncodeDecodeUtils.encodeHex(key));

        byte[] input = "李志伟".getBytes();
        byte[] data = CryptoUtils.aesEncrypt(input, key);
        logger.info(EncodeDecodeUtils.encodeHex(data));

        String str = CryptoUtils.aesDecrypt(data, key);
        logger.info(str);
    }
}
