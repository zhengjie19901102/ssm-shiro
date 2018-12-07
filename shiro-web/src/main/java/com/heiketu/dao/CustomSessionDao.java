package com.heiketu.dao;

import com.heiketu.utils.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomSessionDao extends AbstractSessionDAO {

    @Resource
    private JedisUtil jedisUtil;

    private final String SESSION_PREFIX = "SESSION_PREFIX";

    private byte[] getByteKey(String sessionId){
        if(sessionId != null){
            String newSessionId = SESSION_PREFIX.concat(sessionId);
            byte[] sessionKey = newSessionId.getBytes();
            return sessionKey;
        }

        return null;
    }


    /**
     * 保存session
     * @param session
     * @return
     */
    public void saveSession(Session session){
        if(session!=null && session.getId()!=null){
            byte[] key = getByteKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
            jedisUtil.expire(key,1800);
        }
    }

    /**
     * 有条件查询所有Key
     * @param session_prefix
     * @return
     */
    private Set<byte[]> getKeys(String session_prefix) {
        if(session_prefix != null){
            Set<byte[]> keys = jedisUtil.getKeys(session_prefix);
            return keys;
        }
        return null;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        saveSession(session);
        assignSessionId(session,sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        System.err.println("------ read session ------");

        if(sessionId != null){
            byte[] key = getByteKey(sessionId.toString());
            byte[] value = jedisUtil.get(key);
            Session session = (Session) SerializationUtils.deserialize(value);
            return session;
        }

        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session != null && session.getId() != null){
            String sessionId = session.getId().toString();
            byte[] key = getByteKey(sessionId);
            jedisUtil.delete(key);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {

        Set<byte[]> keys = getKeys(SESSION_PREFIX);
        Set<Session> activeSessions = new HashSet<>();
        if(keys != null){
            for(byte[] key : keys){
                byte[] value = jedisUtil.get(key);
                Session session = (Session)SerializationUtils.deserialize(value);
                activeSessions.add(session);
            }
        }
        return activeSessions;
    }
}
