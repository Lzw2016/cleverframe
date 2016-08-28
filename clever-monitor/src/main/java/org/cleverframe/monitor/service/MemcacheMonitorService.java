package org.cleverframe.monitor.service;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.MemcachedMonitorStatVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-28 17:08 <br/>
 */
@Component(MonitorBeanNames.MemcacheMonitorService)
public class MemcacheMonitorService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(MemcacheMonitorService.class);

    private MemcachedClient memcachedClient;

    public MemcachedClient getMemcachedClient() {
        if (memcachedClient == null) {
            memcachedClient = SpringContextHolder.getBean(SpringBeanNames.MemcachedClient);
        }
        return memcachedClient;
    }

    /**
     * 获取Memcache服务端stats信息<br>
     *
     * @param ipAddress Memcache服务器IP地址
     * @param port      Memcache服务器端口号
     * @return 如果ipAddress不在当前memcachedClient中返回null
     */
    public List<MemcachedMonitorStatVo> getStats(String ipAddress, int port) {
        List<MemcachedMonitorStatVo> statList;
        MemcachedClient client = this.getMemcachedClient();
        InetSocketAddress address = null;
        Collection<InetSocketAddress> servers = memcachedClient.getAvailableServers();
        for (InetSocketAddress server : servers) {
            if (server.getAddress().getHostAddress().equals(ipAddress) && server.getPort() == port) {
                address = server;
                break;
            }
        }
        if (address == null) {
            return null;
        }
        Map<String, String> stats;
        try {
            stats = client.stats(address);
        } catch (Throwable e) {
            logger.error("### 获取Memcache服务端stats信息失败", e);
            return null;
        }
        statList = new ArrayList<>();
        Set<String> keySet = stats.keySet();
        for (String key : keySet) {
            MemcachedMonitorStatVo stat = new MemcachedMonitorStatVo();
            stat.setKey(key);
            stat.setValue(stats.get(key));
            statList.add(stat);
        }
        return statList;
    }

    /**
     * 随机操作Memcache，以便查看监控效果<br/>
     * TODO 测试使用,生产环境删除
     */
    public void test() {
        this.getMemcachedClient();
        try {
            List<String> keyList = new ArrayList<>();
            for (int i = 0; i < 300; i++) {
                long count = (long) (Math.random() * 1000 + 100);
                long sleepTime = (long) (Math.random() * 1000 + 100); // 休眠
                if (Math.random() > 0.8) {
                    // set
                    for (int j = 0; j < count; j++) {
                        String key = UUID.randomUUID().toString();
                        memcachedClient.set(key, 3600, j);
                        keyList.add(key);
                    }
                } else if (Math.random() > 0.6) {
                    // get
                    for (int j = 0; j < keyList.size() && j < count; j++) {
                        if (j % 10 == 6) {
                            memcachedClient.get(UUID.randomUUID().toString());
                        } else {
                            memcachedClient.get(keyList.get(j));
                        }
                    }
                } else if (Math.random() > 0.4) {
                    // delete
                    if (count >= keyList.size()) {
                        count = keyList.size();
                    }
                    List<String> tmp = new ArrayList<>();
                    for (int j = 0; j < count; j++) {
                        tmp.add(keyList.get(j));
                    }
                    for (int j = 0; j < tmp.size(); j++) {
                        if (j % 10 == 7) {
                            memcachedClient.delete(UUID.randomUUID().toString());
                        } else {
                            memcachedClient.delete(tmp.get(j));
                            keyList.remove(tmp.get(j));
                        }
                    }
                } else if (Math.random() > 0.3) {
                    // decr
                    for (int j = 0; j < keyList.size() && j < count; j++) {
                        memcachedClient.decr(keyList.get(j), 1L);
                    }
                } else if (Math.random() > 0.2) {
                    // incr
                    for (int j = 0; j < keyList.size() && j < count; j++) {
                        memcachedClient.incr(keyList.get(j), 1L);
                    }
                } else if (Math.random() > 0.1) {
                    // cas
                    for (int j = 0; j < keyList.size() && j < count; j++) {
                        if (j % 10 == 9) {
                            GetsResponse<String> result = memcachedClient.gets(UUID.randomUUID().toString());
                            if (result != null) {
                                long cas = result.getCas();
                                memcachedClient.cas(UUID.randomUUID().toString(), 3600, UUID.randomUUID().toString(), cas);
                            }
                        } else {
                            GetsResponse<String> result = memcachedClient.gets(keyList.get(j));
                            if (result != null) {
                                long cas = result.getCas();
                                memcachedClient.cas(keyList.get(j), 3600, UUID.randomUUID().toString(), cas);
                            }
                        }
                    }
                }
                Thread.sleep(sleepTime);
                logger.info("随机操作Memcache: " + count);
            }
        } catch (Exception e) {
            logger.error("随机操作Memcache异常", e);
        }
    }
}
