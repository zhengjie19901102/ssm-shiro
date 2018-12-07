package com.heiketu.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Jedis: redis连接相关
 */
@Component
public class JedisUtil {

    @Resource
    private JedisPool jedisPool;

    public Jedis getJedisConnection(){
        Jedis resource = jedisPool.getResource();
        return resource;
    }


    /**
     * 添加数据
     */
    public void set(byte[] key, byte[] value) {
        Jedis connection = getJedisConnection();
        try {
            connection.set(key, value);
        } finally {
            connection.close();
        }
    }

    /**
     * 通过key查询
     */
    public byte[] get(byte[] key) {
        Jedis connection = getJedisConnection();
        byte[] value = null;
        try {
            if(key != null){
                value = connection.get(key);

            }
            return value;
        } finally {
            connection.close();
        }
    }

    /**
     * 删除数据
     */
    public void delete(byte[] key) {
        Jedis connection = getJedisConnection();
        try {
            if(key != null){
                connection.del(key);
            }
        } finally {
            connection.close();
        }
    }


    /**
     * 设置过期时间
     */
    public void expire(byte[] key, int i) {
        Jedis connection = getJedisConnection();
        try {
            connection.expire(key,i);
        } finally {
            connection.close();
        }
    }

    /**
     * 有条件查询所有key
     * @param session_prefix
     * @return
     */
    public Set<byte[]> getKeys(String session_prefix) {
        if(session_prefix != null){
            String key = session_prefix + "*";
            Jedis connection = getJedisConnection();
            Set<byte[]> keys = connection.keys(key.getBytes());
            return keys;
        }
        return null;
    }
}
