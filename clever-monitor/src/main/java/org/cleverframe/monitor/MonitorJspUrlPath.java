package org.cleverframe.monitor;

import org.cleverframe.common.IJspUrlPath;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-18 16:45 <br/>
 */
public class MonitorJspUrlPath implements IJspUrlPath {
    /**
     * Ehcache监控页
     */
    public static final String EhcacheMonitor = "monitor/EhcacheMonitor";

    /**
     * Spring监控页
     */
    public static final String SpringMonitor = "monitor/SpringMonitor";

    /**
     * 应用服务器监控页
     */
    public static final String ServerMonitor = "monitor/ServerMonitor";

    /**
     * Zookeeper监控页
     */
    public static final String ZookeeperMonitor = "monitor/ZookeeperMonitor";
}
