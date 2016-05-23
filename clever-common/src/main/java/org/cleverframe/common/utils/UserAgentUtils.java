package org.cleverframe.common.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户代理字符串识别工具，使用UserAgentUtils实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 21:30 <br/>
 */
public class UserAgentUtils {
    /**
     * 获取用户代理对象
     *
     * @param request Http请求
     * @return UserAgent
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    /**
     * 获取设备类型
     *
     * @param request Http请求
     * @return DeviceType
     */
    public static DeviceType getDeviceType(HttpServletRequest request) {
        return getUserAgent(request).getOperatingSystem().getDeviceType();
    }

    /**
     * 是否是PC
     *
     * @param request Http请求
     * @return 是PC返回true
     */
    public static boolean isComputer(HttpServletRequest request) {
        return DeviceType.COMPUTER.equals(getDeviceType(request));
    }

    /**
     * 是否是手机
     *
     * @param request Http请求
     * @return 是手机返回true
     */
    public static boolean isMobile(HttpServletRequest request) {
        return DeviceType.MOBILE.equals(getDeviceType(request));
    }

    /**
     * 是否是平板
     *
     * @param request Http请求
     * @return 是平板返回true
     */
    public static boolean isTablet(HttpServletRequest request) {
        return DeviceType.TABLET.equals(getDeviceType(request));
    }

    /**
     * 是否是手机或平板
     *
     * @param request Http请求
     * @return 是手机或平板返回true
     */
    public static boolean isMobileOrTablet(HttpServletRequest request) {
        DeviceType deviceType = getDeviceType(request);
        return DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType);
    }

    /**
     * 获取浏览类型
     *
     * @param request Http请求
     * @return Browser
     */
    public static Browser getBrowser(HttpServletRequest request) {
        return getUserAgent(request).getBrowser();
    }

    /**
     * 是否IE版本是否小于等于IE8
     *
     * @param request Http请求
     * @return 浏览器版本小于等于IE8返回true
     */
    public static boolean isLteIE8(HttpServletRequest request) {
        Browser browser = getBrowser(request);
        return Browser.IE5.equals(browser) || Browser.IE6.equals(browser) || Browser.IE7.equals(browser) || Browser.IE8.equals(browser);
    }

}
