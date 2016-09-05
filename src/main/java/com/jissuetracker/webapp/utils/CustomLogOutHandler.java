package com.jissuetracker.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jovin on 22/6/16.


 */

@Component("CustomLogOutHandler")
public class CustomLogOutHandler implements LogoutHandler {

    @Autowired
    SessionRegistry sessionRegistry;


    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        if (NotEmpty.notEmpty(authentication)) {
            Object principalObj = authentication.getPrincipal();
            processLogoutActions(principalObj);
        }
    }


    public void processLogoutActions(Object principalObj) {

        try {
            List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(principalObj, false);
            if (NotEmpty.notEmpty(sessionInformationList)) {
                SessionInformation sessionInformation = sessionRegistry.getAllSessions(principalObj, false).get(0);
                String sessionId = sessionInformation.getSessionId();
                sessionRegistry.removeSessionInformation(sessionId);
                sessionInformation.expireNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
