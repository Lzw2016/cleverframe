package org.cleverframe.generator.format;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Html格式化工具，使用jsoup实现
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 0:03 <br/>
 */
public class HtmlFormatUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(HtmlFormatUtils.class);

    /**
     * 使用Jsoup格式化HTML字符串<br>
     *
     * @param html HTML字符串
     * @return 失败返回 ""
     */
    public static String htmlFormatByJsoup(String html) {
        String result = "";
        try {
            Document document = Jsoup.parse(html);
            document.outputSettings().prettyPrint(true);
            document.outputSettings().indentAmount(4);
            result = document.html();
        } catch (Throwable e) {
            logger.error("HTML格式化失败[Jsoup]", e);
        }
        return result;
    }
}
