package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Menu;
import com.jissuetracker.webapp.models.Roles;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by jovin on 11/2/16.
 */
@Service("sessionObjectsService")
public class SessionObjectsService {

    @Autowired
    RolesService rolesService;

    @Autowired
    UserService userService;

    public void setSessionObjects(Authentication authentication, HttpServletRequest request)throws Exception{
        UserSession userSession = new UserSession();
        userSession.setUser(userService.getUserByUserName(authentication.getName()));
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Roles roles = rolesService.getByName(authorities.iterator().next().getAuthority());

        if(NotEmpty.notEmpty(roles))
        {
            Set<Menu> menuSet = roles.getMenuSet();
            ArrayList<Menu> menuList = new ArrayList<Menu>(menuSet);
            userSession.setMenuList(menuList);
            request.getSession().setAttribute("userSession",userSession);
        }


    }


}
