package org.cleverframe.monitor.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.RedisMonitorInfoVo;
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

    private RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
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
        RedisTemplate client = getRedisTemplate();
        List<RedisMonitorInfoVo> infoList = new ArrayList<>();
        //noinspection Convert2Lambda
        Properties info = (Properties) client.execute(new RedisCallback<Properties>() {
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
        RedisTemplate client = getRedisTemplate();
        //noinspection Convert2Lambda,Anonymous2MethodRef
        return (Long) client.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }
}
