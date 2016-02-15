package com.jissuetracker.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by jovin on 10/2/16.
 */
@Controller
public class MainController {



    @RequestMapping(value = {"/app/","/app/home"})
    public String home(){

      return "home";
    }

    @RequestMapping(value = {"/login","/"})
    public String login(){

        return "login";
    }

}
