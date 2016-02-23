package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.ProjectService;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
            (@RequestBody HashMap<String,Object> projectMap)
            throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Projects projects = new Projects();

        if(projectMap.containsKey("name") && NotEmpty.notEmpty(projectMap.get("name")))
            projects.setName(projectMap.get("name").toString());

        if(projectMap.containsKey("description") && NotEmpty.notEmpty(projectMap.get("description")))
            projects.setDescription(projectMap.get("description").toString());

        if(projectMap.containsKey("users") && NotEmpty.notEmpty(projectMap.get("users"))){
            String usersString = projectMap.get("users").toString();
            String userArray [] = usersString.split(",");
            User userObject = new User();
            HashSet<User> userHashSet = new HashSet<User>();
            for (String user: userArray){
                user = user.replace("[","");
                user = user.replace("]","");
                user = user.replace(" ","");
                System.out.println("Users" + user);
                userObject = userService.getUserByUserName(user);
                if(NotEmpty.notEmpty(userObject))
                    userHashSet.add(userObject);
            }
            projects.setUsers(userHashSet);

        }


            if(NotEmpty.notEmpty(auth.getName()))
                projects.setManager(auth.getName());

        projects.setUrl("/jit/project/"+projects.getName());


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

    @RequestMapping(value = "/projectHomeList")
    @ResponseBody
    public Response projectHomeList(
            @RequestParam(value = "projectName") String projectName)throws Exception{

        Projects project = projectService.projectHomeList(projectName);
        if (NotEmpty.notEmpty(project)){
        Set<User> users = project.getUsers();
        HashMap<String,List<String>> projectUsers = new HashMap<String, List<String>>();
        List<String> projectDetails = new ArrayList<String>();
        projectDetails.add(project.getDescription());
        projectUsers.put("Description",projectDetails);
        if(NotEmpty.notEmpty(users)) {
            for (User user : users) {
                if (user.getRoles().getRolename().equalsIgnoreCase("Manager")) {
                    List<String> manager = new ArrayList<String>();
                    manager.add(user.getName());
                    projectUsers.put("Manager", manager);
                }

                if (user.getRoles().getRolename().equalsIgnoreCase("Developer")) {
                    List<String> developer = new ArrayList<String>();
                    developer.add(user.getName());
                    projectUsers.put("Developers", developer);
                }

                if (user.getRoles().getRolename().equalsIgnoreCase("Tester")) {
                    List<String> tester = new ArrayList<String>();
                    tester.add(user.getName());
                    projectUsers.put("Testers", tester);
                }

            }
        }

        return new Response(projectUsers);
        }

        return new Response("Null");

    }


    @RequestMapping(value = "/{projectName}")
    public String projectHome(@PathVariable (value = "projectName" )
                                  String projectName)throws Exception
    {

        if (projectService.doesUserHasProject(SecurityContextHolder.getContext().getAuthentication().getName(),projectName))
            return "projectHome";
        else
            return "404";
    }

    @RequestMapping("/{name}/projects")
    @ResponseBody public Response userProjects
            (@PathVariable(value = "name")String name)throws Exception{

        User userProjectsObject = userService.getUserByName(name);
        if (NotEmpty.notEmpty(userProjectsObject)) {
            Set<Projects> projectsSet = userProjectsObject.getProjectses();
            HashMap<String,String> userProjectsMap = new HashMap<String, String>();
            for (Projects projects: projectsSet){
                userProjectsMap.put(projects.getName(),projects.getUrl());
            }

            return new Response(userProjectsMap);

        }

        return new Response("Null");
    }

    @RequestMapping("/{name}/issues")
    public String issueHome(@PathVariable(value = "name") String name,
                            HttpServletRequest request)throws Exception{

        Projects project = projectService.getByName(name);
        Set<Issues> issuesSet = new HashSet<Issues>();
        issuesSet=project.getIssueses();

        if(NotEmpty.notEmpty(issuesSet)){
        request.getSession().setAttribute("issues",issuesSet);
            }
        return "projectIssuesHome";
        }

    @RequestMapping("/{name}/userList")
    @ResponseBody
    public Response userDropDownList(@PathVariable(value = "name")String name)throws Exception
    {
        Projects project = projectService.projectHomeList(name);

        if (NotEmpty.notEmpty(project)){

            Set<User> users = project.getUsers();
            HashMap<String,String> projectUsersDropDown = new HashMap<String, String>();
            if (NotEmpty.notEmpty(users)){
                for (User user : users){
                projectUsersDropDown.put(user.getId().toString(),user.getName());
                }

            }

            return new Response(projectUsersDropDown);

        }

        return new Response("Null");

    }


    }



