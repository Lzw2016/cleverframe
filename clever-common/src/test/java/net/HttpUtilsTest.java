package net;

import org.cleverframe.common.net.HttpUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 20:01 <br/>
 */
public class HttpUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtilsTest.class);

    @Test
    public void testHttpGet() throws Exception {
        String url = "http://search.jd.com/Search";
        Map<String, String> param = new HashMap<>();
        param.put("keyword", "电饭煲");
        param.put("enc", "utf-8");
        param.put("wq", "电饭煲");
        String response = HttpUtils.httpGetResultStr(url, param);
        logger.info(response);
    }

    @Test
    public void testHttpPost() throws Exception {
        String url = "http://search.jd.com/Search";
        Map<String, String> param = new HashMap<>();
        param.put("keyword", "电饭煲");
        param.put("enc", "utf-8");
        param.put("wq", "电饭煲");
        String response = HttpUtils.httpPostGetResultStr(url, param);
        logger.info(response);
    }
}
