package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @author huizhe
 * @date 2018/12/7
 * @time 20:47
 */
@Slf4j
public class RedisShardedpoolUtil {

    public static String set(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value);
        } finally {
            RedisShardedPool.returnResource(jedis);
            return result;
        }
    }

    public static Long setnx(String key, String value) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value);
        } finally {
            RedisShardedPool.returnResource(jedis);
            return result;
        }
    }

    public static String get(String key) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key);
        } finally {
            RedisShardedPool.returnResource(jedis);
            return result;
        }
    }

    public static Long del(String key) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("get key:{} error", key);
        } finally {
            RedisShardedPool.returnResource(jedis);
            return result;
        }
    }

    /**
     * 设置key-> value ,并设置时间
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setEx(String key, String value, int exTime) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} error", key, value);
        } finally {
            RedisShardedPool.returnResource(jedis);
            return result;
        }
    }

    /**
     * 设置key 的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key);
        } finally {
            RedisShardedPool.returnResource(jedis);
            return result;
        }
    }
}
