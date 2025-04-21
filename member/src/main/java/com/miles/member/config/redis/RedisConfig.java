package com.miles.member.config.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @Description redis配置
 * @Date 2019/7/11 13:09
 * @Created by 王弘博
 */
@Configuration
public class RedisConfig {

    @Bean(name = "jedis.pool")
    @Resource
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                               @Value("${jedis.pool.host}") String host,
                               @Value("${jedis.pool.port}") int port,
                               @Value("${jedis.pool.timeout}") int timeout,
                               @Value("${jedis.pool.password}") String password,
                               @Value("${jedis.pool.database}") int database) {
        return new JedisPool(config, host, port, timeout, password, database);
    }

    @Bean(name = "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig(@Value("${jedis.pool.config.maxTotal}") int maxTotal,
                                           @Value("${jedis.pool.config.maxIdle}") int maxIdle,
                                           @Value("${jedis.pool.config.maxWaitMillis}") int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }
}
