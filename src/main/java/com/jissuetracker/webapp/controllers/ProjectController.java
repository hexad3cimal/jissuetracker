package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.services.ProjectService;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by jovin on 14/2/16.
 */
@Controller
@RequestMapping("/app/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @RequestMapping("/add")
    public String addProject(){

        return "project";
    }

    @RequestMapping(value = "/addNew",
            method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public Response addNewProject
            (@RequestBody HashMap<String,String> projectMap)
            throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Projects projects = new Projects();

        if(projectMap.containsKey("name") && NotEmpty.notEmpty(projectMap.get("name")))
            projects.setName(projectMap.get("name"));

        if(projectMap.containsKey("description") && NotEmpty.notEmpty(projectMap.get("description")))
            projects.setDescription(projectMap.get("description"));

        if(NotEmpty.notEmpty(auth.getName()))
             projects.setManager(auth.getName());

        projects.setUrl("/jit/projects/"+projects.getName());


        projectService.add(projects);

        return new Response("Success");


    }

    @RequestMapping(value = "/userDropdown",method = RequestMethod.GET)
    @ResponseBody
    public Response userDropdown()throws Exception{

        if (NotEmpty.notEmpty(userService.userDropdownList()))
            return new Response(userService.userDropdownList());
        else
            return new Response("Null");

    }
}
