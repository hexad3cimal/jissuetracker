package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jovin on 19/2/16.
 */
@Controller
@RequestMapping("/app/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/{name}")
    public String userHomePage(@PathVariable(value = "name") String name,
                               HttpServletRequest request)throws Exception{

        User userHomePageObject = userService.getUserByName(name);
        if (NotEmpty.notEmpty(userHomePageObject)) {
            request.getSession().setAttribute("userHomePageObject",userHomePageObject);
            return "userHomePage";
        }
        else
            return "404";

    }
}
