package org.cleverframe.common.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.configuration.BaseConfigNames;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.HttpServletRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 管理不需要进行XXS攻击处理的请求URL
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-8-25 18:24 <br/>
 */
public class XssExcludeUrlUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(XssExcludeUrlUtils.class);

    /**
     * 不需要进行XXS攻击处理的请求地址,不对请求参数进行HTML编码
     */
    private final static Set<String> XSS_EXCLUDE_URL = Collections.synchronizedSet(new HashSet<>());

    /**
     * 不需要进行XXS攻击处理的请求地址 数据保存的文件名称
     */
    private final static String XSS_EXCLUDE_URL_SAVE_FILE;

    static {
        IConfig config = SpringContextHolder.getBean(SpringBeanNames.Config);
        if (config == null) {
            XSS_EXCLUDE_URL_SAVE_FILE = null;
            RuntimeException exception = new RuntimeException("未注入Bean:[" + SpringBeanNames.Config + "]");
            logger.error(exception.getMessage(), exception);
        } else {
            XSS_EXCLUDE_URL_SAVE_FILE = config.getConfig(BaseConfigNames.WEB_FILE_STORAGE_PATH) + File.separator + "XssExcludeUrlSaveFile.txt";
            File file = new File(XSS_EXCLUDE_URL_SAVE_FILE);
            if (!file.exists()) {
                boolean flag = false;
                try {
                    // 不存在就新建文件
                    flag = file.createNewFile();
                } catch (Throwable e) {
                    logger.error("### 创建文件失败:[" + XSS_EXCLUDE_URL_SAVE_FILE + "]", e);
                }
                if (!flag) {
                    String tmp = "### 创建文件失败:[" + XSS_EXCLUDE_URL_SAVE_FILE + "]. 请配置cleverframe.properties文件的值: [" + BaseConfigNames.WEB_FILE_STORAGE_PATH + "]";
                    RuntimeException exception = new RuntimeException(tmp);
                    logger.error(exception.getMessage(), exception);
                }
            }
        }
        logger.info("XSS_EXCLUDE_URL_SAVE_FILE初始化完毕,值[" + XSS_EXCLUDE_URL_SAVE_FILE + "]");
    }

    /**
     * 从文件中加载 不需要进行XXS攻击处理的请求地址
     *
     * @return 返回 不需要进行XXS攻击处理的请求地址 的数量
     */
    public static int loadXSSExcludeUrl() {
        if (StringUtils.isBlank(XSS_EXCLUDE_URL_SAVE_FILE)) {
            logger.error("请配置cleverframe.properties文件的值: [" + BaseConfigNames.WEB_FILE_STORAGE_PATH + "]");
            return 0;
        }
        List<String> lineArray = null;
        try {
            lineArray = FileUtils.readLines(new File(XSS_EXCLUDE_URL_SAVE_FILE));
        } catch (Throwable e) {
            logger.error("### 读取文件失败[" + XSS_EXCLUDE_URL_SAVE_FILE + "]", e);
        }
        if (lineArray == null) {
            return 0;
        }
        for (String line : lineArray) {
            // 以"#"开头表示行注释
            if (line.startsWith("#")) {
                continue;
            }
            line = StringUtils.trim(line);
            XSS_EXCLUDE_URL.add(line);
        }
        return XSS_EXCLUDE_URL.size();
    }

    /**
     * 增加不需要进行XXS攻击处理的请求地址,不对请求参数进行HTML编码
     *
     * @param request 请求对象
     */
    public static void addXSSExcludeUrl(HttpServletRequest request) {
        // 当前请求URL地址
        String requestUrl = HttpServletRequestUtils.getRequestURINotSuffix(request);
        boolean flag = XSS_EXCLUDE_URL.add(requestUrl);
        if (flag && StringUtils.isNotBlank(requestUrl)) {
            if (StringUtils.isBlank(XSS_EXCLUDE_URL_SAVE_FILE)) {
                logger.error("请配置cleverframe.properties文件的值: [" + BaseConfigNames.WEB_FILE_STORAGE_PATH + "]");
                return;
            }
            List<String> lines = new ArrayList<>();
            lines.add(requestUrl);
            try {
                FileUtils.writeLines(new File(XSS_EXCLUDE_URL_SAVE_FILE), lines, true);
            } catch (Throwable e) {
                logger.error("### 写文件文件失败[" + XSS_EXCLUDE_URL_SAVE_FILE + "]", e);
            }
        }
    }

    /**
     * 增加不需要进行XXS攻击处理的请求地址,不对请求参数进行HTML编码
     *
     * @param requestUrl 请求地址,不含后缀
     */
    public static void addXSSExcludeUrl(String requestUrl) {
        XSS_EXCLUDE_URL.add(requestUrl);
    }

    /**
     * 判断请求地址是否存在
     *
     * @return 存在返回true
     */
    public static boolean existsUrl(String requestUrl) {
        return XSS_EXCLUDE_URL.contains(requestUrl);
    }

    /**
     * 判断请求地址是否存在
     *
     * @return 存在返回true
     */
    public static boolean existsUrl(HttpServletRequest request) {
        String requestUrl = HttpServletRequestUtils.getRequestURINotSuffix(request);
        return existsUrl(requestUrl);
    }
}
