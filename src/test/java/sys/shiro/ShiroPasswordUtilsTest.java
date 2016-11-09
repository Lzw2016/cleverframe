package sys.shiro;

import basetest.DaoTestBase;
import org.cleverframe.sys.shiro.ShiroPasswordUtils;
import org.junit.Test;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/9 23:00 <br/>
 */
public class ShiroPasswordUtilsTest extends DaoTestBase {

    @Test
    public void encryptionTest() {
        // e73f30c1f76b416022d0d6c18bc89431511f32d3feef245aefe11a20
        // 67b5c58014314cba4eb16079f031f1f8e22ae63de79d4ebef1a71ffd
        // a9e51596d9bb47d2db444bb92e89081630a72efaccb128749bc169b6
        logger.info(ShiroPasswordUtils.encryption("123456"));
    }
}
