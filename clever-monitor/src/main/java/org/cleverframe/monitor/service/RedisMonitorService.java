package org.cleverframe.monitor.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.RedisMonitorInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 21:33 <br/>
 */
@Component(MonitorBeanNames.RedisMonitorService)
public class RedisMonitorService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(RedisMonitorService.class);

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean(SpringBeanNames.RedisTemplate);
        }
        return redisTemplate;
    }

    /**
     * 获取Redis信息
     *
     * @param section 选择一部分信息 取值: server、clients、memory、persistence、stats、replication、cpu、commandstats、cluster、keyspace
     * @return 返回Redis信息
     */
    public List<RedisMonitorInfoVo> getRedisInfo(final String section) {
        RedisTemplate<String, Object> client = getRedisTemplate();
        List<RedisMonitorInfoVo> infoList = new ArrayList<>();
        //noinspection Convert2Lambda
        Properties info = client.execute(new RedisCallback<Properties>() {
            @Override
            public Properties doInRedis(RedisConnection connection) throws DataAccessException {
                if (StringUtils.isBlank(section)) {
                    return connection.info();
                } else {
                    return connection.info(section);
                }
            }
        });
        Set<Map.Entry<Object, Object>> infoEntrySet = info.entrySet();
        for (Map.Entry<Object, Object> entry : infoEntrySet) {
            RedisMonitorInfoVo redisMonitorInfoVo = new RedisMonitorInfoVo();
            redisMonitorInfoVo.setKey(entry.getKey().toString());
            redisMonitorInfoVo.setValue(entry.getValue().toString());
            infoList.add(redisMonitorInfoVo);
        }
        return infoList;
    }

    /**
     * 获取Redis当前数据数量
     *
     * @return Redis当前数据数量
     */
    public Long getKeyCount() {
        RedisTemplate<String, Object> client = getRedisTemplate();
        //noinspection Convert2Lambda,Anonymous2MethodRef
        return client.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * 获取Redis配置参数的值<br>
     *
     * @param parameter 参数名称，可使用“*”匹配，为空返回所有配置
     * @return 配置信息
     */
    public List<String> getConfig(final String parameter) {
        RedisTemplate<String, Object> client = getRedisTemplate();
        //noinspection Convert2Lambda,unchecked
        return client.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                if (StringUtils.isBlank(parameter)) {
                    return connection.getConfig("*");
                } else {
                    return connection.getConfig(parameter);
                }

            }
        });
    }

    /**
     * 查询Redis key值，查询匹配如下：<br>
     * <li>KEYS * 匹配数据库中所有 key 。</li>
     * <li>KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。</li>
     * <li>KEYS h*llo 匹配 hllo 和 heeeeello 等。</li>
     * <li>KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。</li>
     * <li>特殊符号用 \ 隔开。</li>
     * <b>注意：此操作返回的数据量可能会很大，所以一定要控制pattern参数！</b>
     *
     * @param pattern 查询key匹配字符串
     * @param size    获取的最大数量，取值[1~10000]
     * @return 失败返回 null
     */
    public Set<String> getKeys(String pattern, int size) {
        size = (size <= 0 ? 100 : size);
        size = (size > 10000 ? 10000 : size);
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(pattern.replaceAll("\\**", ""))) {
            logger.warn("参数pattern非法！(pattern 不能是空，或只有“*”)");
            return null;
        }
        Set<String> result = new HashSet<>();
        RedisTemplate<String, Object> client = getRedisTemplate();
        //noinspection unchecked
        Set<String> keySet = client.keys(pattern);
        int i = 0;
        for (String key : keySet) {
            if (i >= size) {
                break;
            }
            result.add(key);
            i++;
        }
        return result;
    }

    /**
     * 获取Key对应的Value，不管其结构
     *
     * @param key key值
     * @return key不存在返回null
     */
    public Object getValue(String key) {
        RedisTemplate<String, Object> client = getRedisTemplate();
        if (!client.hasKey(key)) {
            return null;
        }
        Object value = client.boundValueOps(key).get();
        if (value == null) {
            value = client.boundHashOps(key).entries();
        }
        if (value == null) {
            value = client.boundListOps(key).range(0, -1);
        }
        if (value == null) {
            value = client.boundSetOps(key).members();
        }
        if (value == null) {
            value = client.boundZSetOps(key).range(0, -1);
        }
        return value;
    }

    /**
     * 随机操作Redis，以便查看监控效果<br/>
     * TODO 测试使用,生产环境删除
     */
    public void test() {
        RedisTemplate<String, Object> client = getRedisTemplate();
        List<String> keyList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            long count = (long) (Math.random() * 1000 + 100);
            long sleepTime = (long) (Math.random() * 1000 + 100); // 休眠
            if (Math.random() > 0.5) {
                // 增加key
                for (int j = 0; j < count; j++) {
                    String key = UUID.randomUUID().toString();
                    client.opsForValue().set(key, key);
                    keyList.add(key);
                }
                logger.info("Redis增加数据:" + count);
            } else {
                // 删除key
                if (count >= keyList.size()) {
                    count = keyList.size();
                }
                List<String> tmp = new ArrayList<>();
                for (int j = 0; j < count; j++) {
                    tmp.add(keyList.get(j));
                }
                for (String aTmp : tmp) {
                    client.delete(aTmp);
                    keyList.remove(aTmp);
                }
                logger.info("Redis删除数据:" + count);
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                logger.error("### 线程休眠失败", e);
            }
        }
        logger.info("随机操作Redis，运行完成");
    }
}
