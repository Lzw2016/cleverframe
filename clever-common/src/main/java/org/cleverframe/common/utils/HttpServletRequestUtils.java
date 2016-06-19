package org.cleverframe.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

/**
 * HttpServletRequest工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 23:12 <br/>
 */
public class HttpServletRequestUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(HttpServletRequestUtils.class);

    /**
     * 获取请求的URL地址
     * <pre>
     * 示例：
     * 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
     * request.getRequestURL() http://localhost:8080/CarsiLogCenter_new/idpstat.jsp
     * request.getRequestURI() /CarsiLogCenter_new/idpstat.jsp
     * request.getContextPath()/CarsiLogCenter_new
     * request.getServletPath() /idpstat.jsp
     * request.getQueryString()action=idp.sptopn
     * </pre>
     *
     * @param request 请求对象
     * @return 请求的URL地址
     */
    public static String getRequestUri(HttpServletRequest request) {
        if (request == null) {
            return "";
        } else {
            return request.getRequestURL().toString();
        }
    }

    /**
     * 字符串数组输出
     */
    private static String arrayToString(String[] array) {
        if (array == null || array.length <= 0) {
            return "";
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : array) {
            if (stringBuilder.length() <= 0) {
                stringBuilder.append(str);
            } else {
                stringBuilder.append(",").append(str);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取请求的所有参数
     *
     * @param request 请求对象
     * @return 请求数据值(已解码)
     */
    public static String getRequestParams(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        StringBuilder paramStr = new StringBuilder();
        Set<Map.Entry<String, String[]>> paramMap = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : paramMap) {
            if (paramStr.length() <= 0) {
                paramStr.append(entry.getKey()).append("=").append(arrayToString(entry.getValue()));
            } else {
                paramStr.append("&").append(entry.getKey()).append("=").append(arrayToString(entry.getValue()));
            }
        }
        String result = paramStr.toString();
        try {
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Throwable e) {
            logger.error("### URL解码失败", e);
        }
        return result;
    }


}
