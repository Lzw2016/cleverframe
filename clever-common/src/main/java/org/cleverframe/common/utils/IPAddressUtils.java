package org.cleverframe.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取本机IP地址
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-8-4 16:22 <br/>
 */
public class IPAddressUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(IPAddressUtils.class);

    /**
     * 本机所有的IPv4地址
     */
    private static Set<String> Inet4AddressSet = null;

    /**
     * 获取本机所有的IPv4地址
     *
     * @return 失败返回null
     */
    private static Set<String> getAllIPv4() {
        Set<String> inet4Address = new HashSet<>();
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        inet4Address.add(ip.getHostAddress());
                    }
                }
            }
        } catch (Throwable e) {
            logger.error("### 获取Inet4Address失败", e);
            return null;
        }
        return inet4Address;
    }

    /**
     * 获取本机所有的IPv4地址,已经获取过就直接返回
     *
     * @return 失败返回null
     */
    public static Set<String> getInet4Address() {
        if (Inet4AddressSet == null) {
            Inet4AddressSet = getAllIPv4();
        }
        return Inet4AddressSet;
    }

    /**
     * 重新获取本机所有的IPv4地址
     *
     * @return 失败返回null
     */
    public static Set<String> refreshInet4Address() {
        Inet4AddressSet = getAllIPv4();
        return Inet4AddressSet;
    }

    /**
     * 获取本机所有的IPv4地址,有多个用“;”分隔
     *
     * @return 失败返回"未知"
     */
    public static String getInet4AddressStr() {
        Set<String> inet4Address = getInet4Address();
        if (inet4Address != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : inet4Address) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(stringBuilder.toString())) {
                    stringBuilder.append(';');
                }
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } else {
            return "未知";
        }
    }
}
