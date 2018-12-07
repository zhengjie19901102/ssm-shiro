package com.heiketu.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import java.io.Serializable;

public class CustomWebSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if(sessionKey instanceof WebSessionKey){
            WebSessionKey webSessionKey = (WebSessionKey)sessionKey;
            request = webSessionKey.getServletRequest();
        }

        if(request != null && sessionId != null){
            Session session = (Session) request.getAttribute(sessionId.toString());
            if(session != null){
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if(request != null && sessionId != null && session != null){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}
