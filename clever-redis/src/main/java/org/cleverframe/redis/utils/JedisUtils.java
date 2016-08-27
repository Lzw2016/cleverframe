//package org.cleverframe.redis.utils;
//
//import org.cleverframe.common.reflection.ReflectionsUtils;
//import org.cleverframe.common.spring.SpringBeanNames;
//import org.cleverframe.common.spring.SpringContextHolder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisShardInfo;
//import redis.clients.jedis.exceptions.JedisException;
//import redis.clients.util.Pool;
//
///**
// * 基于Jedis的操作工具类，建议使用RedisTemplate<br>
// *
// * @author LiZW
// * @version 2015年9月22日 下午9:31:09
// * @see org.springframework.data.redis.core.RedisTemplate
// */
//@SuppressWarnings("unchecked")
//public class JedisUtils {
//    /**
//     * 日志对象
//     */
//    protected static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
//
//    /**
//     * jedis连接池抽象类
//     */
//    private static Pool<Jedis> jedisPool;
//
//    static {
//        JedisConnectionFactory jedisConnectionFactory = SpringContextHolder.getBean(SpringBeanNames.JedisConnectionFactory);
//        // 通过反射得到jedis连接池
//        Object object = ReflectionsUtils.getFieldValue(jedisConnectionFactory, "pool");
//        if (object != null && (object instanceof Pool)) {
//            jedisPool = (Pool<Jedis>) object;
//        } else {
//            // 根据spring-context-jedis.xml配置创建jedis连接池
//            logger.warn("### spring-context-jedis.xml 没有配置Redis连接池,创建Redis连接池...");
//            JedisPoolConfig jedisPoolConfig = SpringContextHolder.getBean(SpringBeanNames.JedisPoolConfig);
//            assert jedisConnectionFactory != null;
//            JedisShardInfo shardInfo = jedisConnectionFactory.getShardInfo();
//            jedisPool = new JedisPool(jedisPoolConfig, shardInfo.getHost(), shardInfo.getPort(), shardInfo.getSoTimeout(), shardInfo.getPassword());
//        }
//    }
//
//    /**
//     * 获取Redis连接
//     *
//     * @return Redis连接
//     * @throws JedisException
//     */
//    public static Jedis getResource() throws JedisException {
//        Jedis jedis;
//        try {
//            jedis = jedisPool.getResource();
//        } catch (JedisException e) {
//            logger.warn("### 获取Redis连接异常", e);
//            throw e;
//        }
//        return jedis;
//    }
//
//    /**
//     * 释放Redis连接到连接池
//     *
//     * @param jedis Redis连接
//     */
//    public static void returnResource(Jedis jedis) {
//        if (jedis != null) {
//            jedisPool.returnResource(jedis);
//        }
//    }
//
//    /**
//     * 归还不能使用的Redis连接
//     *
//     * @param jedis Redis连接
//     */
//    private static void returnBrokenResource(Jedis jedis) {
//        if (jedis != null) {
//            jedisPool.returnBrokenResource(jedis);
//        }
//    }
//}
