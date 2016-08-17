package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.dto.ProjectDto;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.ProjectService;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.GetCurrentUserDetails;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import com.jissuetracker.webapp.utils.XssCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    GetCurrentUserDetails getCurrentUserDetails;


    @RequestMapping("/")
    public String project() {

        return "project";
    }

    //handles the add new project view resolution request
    @RequestMapping("/add")
    public ModelAndView addProject(@ModelAttribute ProjectDto projectDto) throws Exception {
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {

            //only users with admin or manager role can add new project
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1 || getCurrentUserDetails.getDetails().getRoles().getId() == 2) {

                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("projectDto", projectDto);
                modelAndView.setViewName("addProject");
                return modelAndView;
            }
        }
        return new ModelAndView("404");

    }

    //handles the edit project view resolution request
    @RequestMapping("/edit/{project}")
    public ModelAndView editProject(@PathVariable(value = "project") String project,
                                    @ModelAttribute ProjectDto projectDto) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {

            //only users with admin role or project manager can edit project
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1
                    || (getCurrentUserDetails.getDetails().getRoles().getId() == 2
                    && projectService.doesUserHasProject(getCurrentUserDetails.getDetails().getEmail(), project))) {
                Projects projectObject = projectService.getByProjectNameAlongWithIssuesAndUsers(project);
                if (NotEmpty.notEmpty(projectObject)) {
                    if (NotEmpty.notEmpty(projectObject.getId()))
                        projectDto.setId(projectObject.getId());
                    if (NotEmpty.notEmpty(projectObject.getDescription()))
                        projectDto.setDescription(projectObject.getDescription());
                    if (NotEmpty.notEmpty(projectObject.getName()))
                        projectDto.setName(projectObject.getName());
                    if (NotEmpty.notEmpty(projectObject.getUsers())) {
                        List<String> userList = new ArrayList<String>();
                        for (User u : projectObject.getUsers()) {
                            userList.add(u.getEmail());
                        }

                        projectDto.setUserList(userList);
                    }
                }
                modelAndView.addObject("projectDto", projectDto);
                modelAndView.setViewName("addProject");
                return modelAndView;
            } else
                return new ModelAndView("404");

        } else
            return new ModelAndView("404");
    }


    //handles the add new/edit project ajax request
    //XssCleaner class is used for cleaning malicious code if present
    @RequestMapping(value = "/addNew",
            method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addNewProject
            (@RequestBody HashMap<String, Object> projectMap)
            throws Exception {


        Projects projects = new Projects();

        //determines whether edit or add
        //if given hashmap contains id key with valid value objwct with that id will be loaded
        if (projectMap.containsKey("id") && NotEmpty.notEmpty(projectMap.get("id").toString())) {
            projects = projectService.getById(Integer.parseInt(projectMap.get("id").toString()));
        }

        if (projectMap.containsKey("name") && NotEmpty.notEmpty(projectMap.get("name"))) {

            projects.setName(XssCleaner.clean(projectMap.get("name").toString()));
        }

        if (projectMap.containsKey("description") && NotEmpty.notEmpty(projectMap.get("description"))) {
            projects.setDescription(XssCleaner.clean(projectMap.get("description").toString()));
        }

        if (projectMap.containsKey("users") && NotEmpty.notEmpty(projectMap.get("users"))) {
            String usersString = projectMap.get("users").toString();
            String userArray[] = usersString.split(",");
            User userObject = new User();
            HashSet<User> userHashSet = new HashSet<User>();
            if (NotEmpty.notEmpty(userArray)) {
                for (String user : userArray) {
                    user = user.replace("[", "");
                    user = user.replace("]", "");
                    user = user.replace(" ", "");
                    userObject = userService.getUserByEmail(user);
                    if (NotEmpty.notEmpty(userObject))
                        userHashSet.add(userObject);
                }
            }

            projects.setUsers(userHashSet);

        }


        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {


            projects.setManager(getCurrentUserDetails.getDetails().getEmail());

            if (NotEmpty.notEmpty(projects.getName()))
                projects.setUrl("/jit/app/project/" + projects.getName());


            if (projects.getId() == null &&
                    (getCurrentUserDetails.getDetails().getRoles().getId() == 1
                            || (getCurrentUserDetails.getDetails().getRoles().getId() == 2))) {
                projectService.add(projects);
                return new Response("Success");


            } else {
                projectService.update(projects);
                return new Response("Success");
            }
        }

        return new Response("Error");


    }

    //produces json response containg available users
    @RequestMapping(value = "/userDropdown", method = RequestMethod.GET)
    @ResponseBody
    public Response userDropdown() throws Exception {

        if (NotEmpty.notEmpty(userService.userDropdownList()))
            return new Response(userService.userDropdownList());
        else
            return new Response("Null");

    }

   //produces values used in the project home page
    @RequestMapping(value = "/projectHomeList")
    @ResponseBody
    public Response projectHomeList(
            @RequestParam(value = "projectName") String projectName) throws Exception {

        Projects project = projectService.getByProjectNameAlongWithUsers(projectName);
        if (NotEmpty.notEmpty(project)) {
            Set<User> users = project.getUsers();
            HashMap<String, List<String>> projectUsers = new HashMap<String, List<String>>();
            List<String> projectDetails = new ArrayList<String>();
            projectDetails.add(project.getDescription());
            projectUsers.put("Description", projectDetails);
            if (NotEmpty.notEmpty(users)) {
                for (User user : users) {
                    if (NotEmpty.notEmpty(user.getRoles())) {
                        {

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
                            if (user.getRoles().getRolename().equalsIgnoreCase("Reporter")) {
                                List<String> reporters = new ArrayList<String>();
                                reporters.add(user.getName());
                                projectUsers.put("Reporters", reporters);
                            }
                        }
                    }
                    if (NotEmpty.notEmpty(project.getManager())) {
                        if (NotEmpty.notEmpty(userService.getUserByEmail(project.getManager()))) ;
                        List<String> manager = new ArrayList<String>();
                        manager.add(userService.getUserByEmail(project.getManager()).getName());
                        projectUsers.put("Manager", manager);
                    }
                }

                return new Response(projectUsers);
            }
        }
        return new Response("Null");

    }


    //handles view resolution request of project home page
    @RequestMapping(value = "/{projectName}")
    public String projectHome(@PathVariable(value = "projectName")
                              String projectName) throws Exception {
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {

            //administrator can view any project
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1)
                return "projectIssues";

            //normal user can view projects only which they are part of
            else if (projectService.doesUserHasProject(getCurrentUserDetails.getDetails().getEmail(), projectName))
                return "projectIssues";
            else
                return "404";


        } else
            return "404";
    }


    //produces json response of given user project
    @RequestMapping("/{userId}/projects")
    @ResponseBody
    public Response userProjects
            (@PathVariable(value = "userId") Integer userId) throws Exception {

        User userProjectsObject = userService.getUserById(userId);
        if (NotEmpty.notEmpty(userProjectsObject)) {
            Set<Projects> projectsSet = userProjectsObject.getProjectses();
            HashMap<String, String> userProjectsMap = new HashMap<String, String>();
            for (Projects projects : projectsSet) {
                userProjectsMap.put(projects.getName(), projects.getUrl());
            }

            return new Response(userProjectsMap);

        }

        return new Response("Null");
    }


    //produces json response of given project's issues
    @RequestMapping("/{name}/issues")
    public String issueHome(@PathVariable(value = "name") String name,
                            HttpServletRequest request) throws Exception {

        Projects project = projectService.getByProjectNameAlongWithIssuesAndUsers(name);
        Set<Issues> issuesSet = new HashSet<Issues>();

        if (NotEmpty.notEmpty(project)){
            issuesSet = project.getIssueses();

            if (NotEmpty.notEmpty(issuesSet)) {
                request.getSession().setAttribute("issues", issuesSet);
            }
            return "projectIssuesHome";
        }else
            return "404";
    }


    //produces json response of given project's users
    @RequestMapping("/{name}/userList")
    @ResponseBody
    public Response userDropDownList(@PathVariable(value = "name") String name) throws Exception {
        Projects project = projectService.getByProjectNameAlongWithUsers(name);

        if (NotEmpty.notEmpty(project)) {

            Set<User> users = project.getUsers();
            HashMap<String, String> projectUsersDropDown = new HashMap<String, String>();
            if (NotEmpty.notEmpty(users)) {
                for (User user : users) {
                    projectUsersDropDown.put(user.getId().toString(), user.getName());
                }

            }

            return new Response(projectUsersDropDown);

        }

        return new Response("Null");

    }


    //project list based on user
    @RequestMapping("/list")
    @ResponseBody
    public Response projectList() throws Exception {

        return new Response(projectService.projectsList());
    }


}



