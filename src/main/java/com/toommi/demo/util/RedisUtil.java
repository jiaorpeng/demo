package com.toommi.demo.util;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * redis 操作类
 * @author 777H
 *
 */
@Component
@RestController
public class RedisUtil {

//    private StringRedisTemplate stringRedis;

    @Resource
    private StringRedisTemplate stringRedis;


    public StringRedisTemplate getStringRedis() {
        return stringRedis;
    }

    public void setStringRedis(StringRedisTemplate stringRedis) {
        this.stringRedis = stringRedis;
    }

    /**
     * 设置key-value
     */
    public String set(String key, String value) {
        try {

            stringRedis.opsForValue().set(key, value);
        } catch (Exception e) {
            return "error";
        }
        return "ok";
    }

    /**
     * 获得key对应value
     */
    public String get(String key) {

        return stringRedis.opsForValue().get(key);
    }

    /**
     * 设置key-value并在seconds秒后过期
     *
     * @param key
     * @param seconds	单位秒
     * @param value
     * @return
     */
    public String setex(final String key, final long seconds, final String value) {

        Object results = stringRedis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                try {
                    ((StringRedisConnection) connection).setEx(key, seconds, value);
                } catch (Exception ex) {
                    return "error";
                }
                return "ok";
            }
        });
        return results + "";
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void delete(final String key) {
        stringRedis.delete(key);
    }

    /**
     * 获取redis服务器时间戳
     *
     * @return
     */
    public Long time() {
        Object results = stringRedis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                try {
                    return ((StringRedisConnection) connection).time();
                } catch (Exception ex) {
                    return -1;
                }
            }
        });
        return (Long) results;
    }

    /**
     * 判断KEY是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        Object results = stringRedis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                return ((StringRedisConnection) connection).exists(key);
            }
        });
        return (boolean) results;
    }

    /**
     * 数字型key加1
     */
    public String incr(final String key) {

        Object results = stringRedis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return ((StringRedisConnection) connection).incr(key);
            }
        });
        return results + "";
    }

    /**
     * 数字型key减1
     */
    public String decr(final String key) {

        Object results = stringRedis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return ((StringRedisConnection) connection).decr(key);
            }
        });
        return results + "";
    }
    /**
     * 设置MAP
     * @param key
     * @param o
     * @return
     */

    public void setObject(final String key,Map<String,Object>o){
        stringRedis.opsForHash().putAll(key,  o);


    }
    /**
     * 获取MAP
     * @param key
     * @param
     * @return
     */

    public Map<Object, Object> getObject(final String key){
        return stringRedis.opsForHash().entries(key);

    }

    /**
     * 设置key-value并在seconds秒后过期
     *
     * @param key
     * @param seconds	单位秒
     * @param value
     * @return
     */
    public String psetex (final String key, final long seconds, final String value) {
        Object results = stringRedis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                try {
                    ((StringRedisConnection) connection).pSetEx(key, seconds, value);
                } catch (Exception ex) {
                    return "error";
                }
                return "ok";
            }
        });
        return results + "";
    }

}
