package com.heiketu.shiro;

import com.heiketu.utils.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

@Component
public class CustomCache<K,V> implements Cache<K,V> {

    @Resource
    private JedisUtil jedisUtil;

    @Override
    public V get(K k) throws CacheException {
        System.out.println("----- 缓存中获取数据 -----");
        byte[] key = null;
        if(k instanceof String){
            key = ((String) k).getBytes();
        }else{
            key = SerializationUtils.serialize(k);
        }
        byte[] value = jedisUtil.get(key);
        if(value != null){
            return (V)SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = null;
        if(k instanceof String){
            key = ((String) k).getBytes();
        }else{
            key = SerializationUtils.serialize(k);
        }
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = null;
        if(k instanceof String){
            key = ((String) k).getBytes();
        }else{
            key = SerializationUtils.serialize(k);
        }
        byte[] value = jedisUtil.get(key);
        jedisUtil.delete(key);

        return (V) SerializationUtils.deserialize(value);
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
