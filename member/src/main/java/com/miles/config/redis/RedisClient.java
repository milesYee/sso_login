package com.miles.config.redis;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description redis常用方法
 * @Date 2019/7/11 13:09
 * @Created by 王弘博
 */
@Component
public class RedisClient {

    private Logger logger = LoggerFactory.getLogger(RedisClient.class);

    @Resource
    private JedisPool jedisPool;

    public Long del(String key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            if (jedis.exists(key)) {
                value = jedis.del(key);
                this.logger.debug("del {} = {}", key, value);
            }
        } catch (Exception e) {
            this.logger.error("del {} = {}", new Object[]{key, value, e});
        } finally {
            this.release(jedis);
        }
        return value;
    }

    public String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                this.logger.debug("get {} = {}", key, value);
            }
        } catch (Exception e) {
            this.logger.error("get {} = {}", new Object[]{key, value, e});
        } finally {
            this.release(jedis);
        }
        return value;
    }

    public <T> T getObject(String key, Class<T> clazz) {
        if(!this.exists(key)){
            return null;
        }
        String value = this.get(key);
        return JSONUtil.toBean(value, clazz);
    }

    public String setObject(String key, Object object, int expire) {
        String jsonStr = JSONUtil.toJsonStr(object);
        return this.set(key,jsonStr,expire);
    }

    public String set(String key, String value) {
        return this.set(key, value, 0);
    }

    public String set(String key, String value, int expire) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = this.getResource();
            result = jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
            logger.debug("set {} = {}", key, value);
        } catch (Exception e) {
            logger.error("set {} = {}", new Object[]{key, value, e});
        } finally {
            this.release(jedis);
        }
        return result;
    }

    public boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            logger.error("exists {}", key, e);
        } finally {
            this.release(jedis);
        }
        return result;
    }

    /**
     * ttl
     *
     * @param key
     * @return
     */
    public Long ttl(String key) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            result = jedis.ttl(key);
        } catch (Exception e) {
            logger.error("exists {}", key, e);
        } finally {
            this.release(jedis);
        }
        return result;
    }

    public Long incr(String key) {
        return incrWithExpire(key, 0);
    }

    public Long incrWithExpire(String key, int seconds) {
        Jedis jedis = null;
        Long incr;
        try {
            if (StringUtils.isBlank(key)) {
                incr = null;
                return incr;
            }
            jedis = this.getResource();
            incr = jedis.incr(key);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            logger.error("incr {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
        return incr;
    }

    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        Long incrBy;
        try {
            if (StringUtils.isBlank(key)) {
                incrBy = null;
                return incrBy;
            }
            if (integer == null) {
                integer = 0L;
            }
            jedis = this.getResource();
            incrBy = jedis.incrBy(key, integer);
        } catch (Exception e) {
            logger.error("incr {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
        return incrBy;
    }

    public Long incrByWithExpire(String key, Long integer, int seconds) {
        Jedis jedis = null;
        Long incrBy;
        try {
            if (StringUtils.isBlank(key)) {
                incrBy = null;
                return incrBy;
            }
            if (integer == null) {
                integer = 0L;
            }
            jedis = this.getResource();
            incrBy = jedis.incrBy(key, integer);

            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            logger.error("incr {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
        return incrBy;
    }

    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;
        Long decrBy;
        try {
            if (StringUtils.isBlank(key)) {
                decrBy = null;
                return decrBy;
            }
            if (integer == null) {
                integer = 0L;
            }
            jedis = this.getResource();
            decrBy = jedis.decrBy(key, integer);
        } catch (Exception e) {
            logger.error("incr {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
        return decrBy;
    }

    public Long decr(String key) {
        Jedis jedis = null;
        Long decr;
        try {
            if (StringUtils.isBlank(key)) {
                decr = null;
                return decr;
            }
            jedis = this.getResource();
            decr = jedis.decr(key);
            return decr;
        } catch (Exception e) {
            logger.error("decr {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * hgetAll
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> map = new HashMap<>();
        Jedis jedis = null;
        try {
            if (StringUtils.isBlank(key)) {
                return map;
            }
            jedis = this.getResource();
            map = jedis.hgetAll(key);
            return map;
        } catch (Exception e) {
            logger.error("hgetAll {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * hmset
     *
     * @param key
     * @return
     */
    public String hmset(String key, Map<String, String> map) {
        Jedis jedis = null;
        try {
            if (StringUtils.isBlank(key)) {
                return null;
            }
            jedis = this.getResource();
            return jedis.hmset(key, map);
        } catch (Exception e) {
            logger.error("hmset {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * hdel
     *
     * @param key
     * @param fields
     * @return
     */
    public Long hdel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            logger.error("hdel {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * llen(获取列表长度)
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            logger.error("llen {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * lpushx(将一个值插入到已存在的列表头部)
     *
     * @param key
     * @param value
     * @return
     */
    public Long lpushx(String key, String value) {
        return lpush(key, value);
    }

    /**
     * lpush(将一个或多个值插入到列表头部)
     *
     * @param key
     * @param fields
     * @return
     */
    public Long lpush(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            return jedis.lpush(key, fields);
        } catch (Exception e) {
            logger.error("lpush {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * rpop(移除列表的最后一个元素，返回值为移除的元素)
     *
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            return jedis.rpop(key);
        } catch (Exception e) {
            logger.error("rpop {} ", key, e);
            return null;
        } finally {
            this.release(jedis);
        }
    }


    /**
     * 获取分布式锁
     *
     * @param lockKey
     * @return
     */
    public boolean getLock(String lockKey) {
        return this.getLock(lockKey, "1", 5);
    }

    /**
     * 获取分布式锁
     *
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    public boolean getLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            String result = jedis.set(lockKey, requestId, "NX", "EX", expireTime);
            if ("OK".equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("getLock {} ", lockKey, e);
            return false;
        } finally {
            this.release(jedis);
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey
     * @return
     */
    public boolean unLock(String lockKey) {
        return this.unLock(lockKey, "1");
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey
     * @param requestId
     * @return
     */
    public boolean unLock(String lockKey, String requestId) {
        Jedis jedis = null;
        try {
            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                    "    return redis.call(\"del\",KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";
            jedis = this.getResource();
            Object result = jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(requestId));
            if ("OK".equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("unLock {} ", lockKey, e);
            return false;
        } finally {
            this.release(jedis);
        }
    }

    private Jedis getResource() throws Exception {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            logger.error("getResource.", e);
            throw e;
        }
    }

    private void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}