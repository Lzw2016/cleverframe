package org.cleverframe.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * MAC地址工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-10 2:05 <br/>
 */
public class MacAddressUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(MacAddressUtils.class);

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取Unix网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            /**
             * Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            int index;
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 寻找标示字符串[hwaddr]
                 */
                index = line.toLowerCase().indexOf("hwaddr");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
        return mac;
    }

    /**
     * 获取Linux网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getLinuxMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            /**
             * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            int index;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("硬件地址");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + 4).trim();
                    break;
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
        // 取不到，试下Unix取发
        if (mac == null) {
            return getUnixMACAddress();
        }
        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            /**
             * windows下的命令，显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int index;
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 寻找标示字符串[physical address]
                 */
//				index = line.toLowerCase().indexOf("physical address");
//				if (index != -1) {
                if (line.split("-").length == 6) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        /**
                         * 取出mac地址并去除2边空格
                         */
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
                index = line.toLowerCase().indexOf("物理地址");
                if (index != -1) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        /**
                         * 取出mac地址并去除2边空格
                         */
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
        return mac;
    }

    /**
     * 获取系统网卡Mac地址
     */
    public static String getMac() {
        String os = getOSName();
        String mac;
        if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
        } else if (os.startsWith("linux")) {
            mac = getLinuxMACAddress();
        } else {
            mac = getUnixMACAddress();
        }
        return mac == null ? "" : mac;
    }
}
