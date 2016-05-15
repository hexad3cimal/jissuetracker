package com.jissuetracker.webapp.utils;

import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * Created by jovin on 12/4/16.
 */

//for providing the logged in user object in the controllers
@Component("GetCurrentUserDetails")
public class GetCurrentUserDetails{

    @Autowired
    UserService userService;

      public User getDetails() throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            User user = userService.getUserByEmail(auth.getName());
            return user;

        }else
            return null;
    }
}
