package org.cleverframe.common.net;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http请求工具,使用HttpClient实现
 * <b>参考文档:<a href="http://hc.apache.org/httpcomponents-client-ga/examples.html">查看文档</a></b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 19:40 <br/>
 */
public class HttpUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 返回数据默认编码
     */
    private final static String responseDefaultCharset = "UTF-8";

    // --------------------------------------------------------------------------------------------
    // GET请求
    // --------------------------------------------------------------------------------------------

    /**
     * 生成请求参数(会自动编码)
     *
     * @param paramMap 请求参数集合
     * @return 生成请求参数, 如: keyword=%E7%94&enc=utf-8&wq=%AD%E7%85%B2&pv=j0rxs
     */
    private static String getParamStr(Map<String, String> paramMap) {
        StringBuilder param = new StringBuilder();
        if (paramMap != null && paramMap.size() > 0) {
            Set<Map.Entry<String, String>> paramSet = paramMap.entrySet();
            for (Map.Entry<String, String> entry : paramSet) {
                if (StringUtils.isBlank(entry.getKey())) {
                    continue;//参数名为空
                }
                String key = EncodeDecodeUtils.urlEncode(entry.getKey());
                String value = EncodeDecodeUtils.urlEncode(entry.getValue());
                if (StringUtils.isBlank(param.toString())) {
                    param.append(key).append("=").append(value);
                } else {
                    param.append("&").append(key).append("=").append(value);
                }
            }
        }
        return param.toString();
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url            请求URL地址
     * @param paramMap       请求参数(会自动编码)
     * @param defaultCharset 设置返回结果的编码
     * @return 成功返回请求结果，失败返回null
     */
    public static String httpGetResultStr(final String url, Map<String, String> paramMap, final String defaultCharset) {
        String response = null;
        String param = getParamStr(paramMap);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(StringUtils.isBlank(param) ? url : (url + "?" + param));
            logger.debug("### 执行请求 - {}", httpget.getRequestLine());
            HttpResponse httpResponse = httpclient.execute(httpget);
            logger.debug("HTTP GET请求,状态码=" + httpResponse.getStatusLine().getStatusCode());
            if (StringUtils.isBlank(defaultCharset)) {
                response = EntityUtils.toString(httpResponse.getEntity(), responseDefaultCharset);
            } else {
                response = EntityUtils.toString(httpResponse.getEntity(), defaultCharset);
            }
        } catch (Throwable e) {
            logger.error("### HttpGet请求异常 - [" + url + "]", e);
        } finally {
            try {
                httpclient.close();
            } catch (Throwable e) {
                logger.error("### CloseableHttpClient.close() 异常", e);
            }
        }
        return response;
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url      请求URL地址
     * @param paramMap 请求参数(会自动编码)
     * @return 成功返回请求结果，失败返回null
     */
    public static String httpGetResultStr(final String url, Map<String, String> paramMap) {
        return httpGetResultStr(url, paramMap, null);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求URL地址
     * @return 成功返回请求结果，失败返回null
     */
    public static String httpGetResultStr(final String url) {
        return httpGetResultStr(url, null, null);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，字节数组
     *
     * @param url      请求URL地址
     * @param paramMap 请求参数(会自动编码)
     * @return 成功返回请求结果，失败返回null
     */
    public static byte[] httpGetResultByte(final String url, Map<String, String> paramMap) {
        byte[] response = null;
        String param = getParamStr(paramMap);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(StringUtils.isBlank(param) ? url : (url + "?" + param));
            logger.debug("### 执行请求 - {}", httpget.getRequestLine());
            HttpResponse httpResponse = httpclient.execute(httpget);
            logger.debug("HTTP GET请求,状态码=" + httpResponse.getStatusLine().getStatusCode());
            response = EntityUtils.toByteArray(httpResponse.getEntity());
        } catch (Throwable e) {
            logger.error("### HttpGet请求异常 - [" + url + "]", e);
        } finally {
            try {
                httpclient.close();
            } catch (Throwable e) {
                logger.error("### CloseableHttpClient.close() 异常", e);
            }
        }
        return response;
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，字节数组
     *
     * @param url 请求URL地址
     * @return 成功返回请求结果，失败返回null
     */
    public static byte[] httpGetResultByte(final String url) {
        return httpGetResultByte(url, null);
    }

    // --------------------------------------------------------------------------------------------
    // POST请求
    // --------------------------------------------------------------------------------------------

    /**
     * 生成请求参数(会自动编码)
     *
     * @param paramMap 请求参数集合
     * @return 生成请求参数, 如: keyword=%E7%94&enc=utf-8&wq=%AD%E7%85%B2&pv=j0rxs
     */
    private static List<NameValuePair> getParamList(Map<String, String> paramMap) {
        List<NameValuePair> nameValuePairList = null;
        if (paramMap != null && paramMap.size() > 0) {
            nameValuePairList = new ArrayList<>();
            Set<Map.Entry<String, String>> paramSet = paramMap.entrySet();
            for (Map.Entry<String, String> entry : paramSet) {
                if (StringUtils.isBlank(entry.getKey())) {
                    continue;//参数名为空
                }
                BasicNameValuePair param = new BasicNameValuePair(entry.getKey(), entry.getValue());
                nameValuePairList.add(param);
            }
        }
        return nameValuePairList;
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url            请求URL地址
     * @param paramMap       请求参数(会自动编码)
     * @param defaultCharset 设置返回结果的编码
     * @return 成功返回请求结果，失败返回null
     */
    public static String httpPostGetResultStr(String url, Map<String, String> paramMap, final String defaultCharset) {
        String response = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<NameValuePair> nameValuePairList = getParamList(paramMap);
        try {
            HttpPost httpPost = new HttpPost(url);
            if (nameValuePairList != null && nameValuePairList.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, Consts.UTF_8));
            }
            // httpPost.setHeader("Accept", "application/json");
            // httpPost.setHeader("Content-type", "application/json");
            logger.debug("### 执行请求 - {}", httpPost.getRequestLine());
            HttpResponse httpResponse = httpclient.execute(httpPost);
            logger.debug("HTTP POST请求,状态码=" + httpResponse.getStatusLine().getStatusCode());
            if (StringUtils.isBlank(defaultCharset)) {
                response = EntityUtils.toString(httpResponse.getEntity(), responseDefaultCharset);
            } else {
                response = EntityUtils.toString(httpResponse.getEntity(), defaultCharset);
            }
        } catch (Throwable e) {
            logger.error("### HttpPost请求异常 - [" + url + "]", e);
        } finally {
            try {
                httpclient.close();
            } catch (Throwable e) {
                logger.error("### CloseableHttpClient.close() 异常", e);
            }
        }
        return response;
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url      请求URL地址
     * @param paramMap 请求参数(会自动编码)
     * @return 成功返回请求结果，失败返回null
     */
    public static String httpPostGetResultStr(String url, Map<String, String> paramMap) {
        return httpPostGetResultStr(url, paramMap, null);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求URL地址
     * @return 成功返回请求结果，失败返回null
     */
    public static String httpPostGetResultStr(String url) {
        return httpPostGetResultStr(url, null, null);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字节数组
     *
     * @param url      请求URL地址
     * @param paramMap 请求参数(会自动编码)
     * @return 成功返回请求结果，失败返回null
     */
    public static byte[] httpPostGetResultByte(String url, Map<String, String> paramMap) {
        byte[] response = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<NameValuePair> nameValuePairList = getParamList(paramMap);
        try {
            HttpPost httpPost = new HttpPost(url);
            if (nameValuePairList != null && nameValuePairList.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, Consts.UTF_8));
            }
            // httpPost.setHeader("Accept", "application/json");
            // httpPost.setHeader("Content-type", "application/json");
            logger.debug("### 执行请求 - {}", httpPost.getRequestLine());
            HttpResponse httpResponse = httpclient.execute(httpPost);
            logger.debug("HTTP POST请求,状态码=" + httpResponse.getStatusLine().getStatusCode());
            response = EntityUtils.toByteArray(httpResponse.getEntity());
        } catch (Throwable e) {
            logger.error("### HttpPost请求异常 - [" + url + "]", e);
        } finally {
            try {
                httpclient.close();
            } catch (Throwable e) {
                logger.error("### CloseableHttpClient.close() 异常", e);
            }
        }
        return response;
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字节数组
     *
     * @param url 请求URL地址
     * @return 成功返回请求结果，失败返回null
     */
    public static byte[] httpPostGetResultByte(String url) {
        return httpPostGetResultByte(url, null);
    }
}
