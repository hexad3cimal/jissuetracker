package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.dto.UserDto;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.IssueService;
import com.jissuetracker.webapp.services.ProjectService;
import com.jissuetracker.webapp.services.RolesService;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.GetCurrentUserDetails;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.PasswordEncoder;
import com.jissuetracker.webapp.utils.Response;
import com.jissuetracker.webapp.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 19/2/16.
 */
@Controller
@RequestMapping("/app/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RolesService rolesService;

    @Autowired
    ProjectService projectService;

    @Autowired
    GetCurrentUserDetails getCurrentUserDetails;

    @Autowired
    IssueService issueService;


    //handles user home page requests
    @RequestMapping("/")
    public String home() throws Exception {
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {

            //if logged in user is admin then user list will be shown
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1)

                return "user";

            //if logged in user is not admin then user specific page  will be shown
            return "userHomePage";
        } else
            return "404";

    }

    //handles user list requests
    @RequestMapping("/user")
    public String user() throws Exception {
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {

            //only administrators can view user list
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1)

                return "user";
            else
                return "404";

        } else
            return "404";

    }


    @RequestMapping("/homepageData/{userId}")
    @ResponseBody
    public Response homepageData(@PathVariable(value = "userId") Integer userId) throws Exception {
        HashMap<String, Integer> stats = new HashMap<String, Integer>();
        if (NotEmpty.notEmpty(userId)) {
            Integer unreadIssueCount = 0;

            List<Issues> issuesList = issueService.getUserIssuesByUserId(userId);

            if (NotEmpty.notEmpty(issuesList)) {
                stats.put("issueCount", issuesList.size());
                for (int i = 0; i < issuesList.size(); i++) {
                    if (NotEmpty.notEmpty(issuesList.get(i))) {
                        if (NotEmpty.notEmpty(issuesList.get(i).getReadByAssigned())) {
                            if (issuesList.get(i).getReadByAssigned().equalsIgnoreCase("false")) {
                                ++unreadIssueCount;
                            }
                        }
                    }
                }
                stats.put("unreadIssueCount", unreadIssueCount);


            }
        }
        return new Response(stats);
    }

    @RequestMapping("/add")
    public ModelAndView addUserPage() throws Exception {

        UserDto user = new UserDto();
        ModelAndView modelAndView = new ModelAndView();
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1) {
                modelAndView.setViewName("addUser");
                modelAndView.addObject("user", user);
            } else
                modelAndView.setViewName("404");
            return modelAndView;

        } else {
            modelAndView.setViewName("404");
            return modelAndView;
        }
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable(value = "id") Integer id) throws Exception {

        UserDto userDto = new UserDto();
        ModelAndView modelAndView = new ModelAndView();

        if (id != null) {
            if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
                if (getCurrentUserDetails.getDetails().getRoles().getId() == 1) {
                    User user = userService.getUserById(id);
                    populateUserDto(user, userDto);
                    modelAndView.setViewName("addUser");
                    modelAndView.addObject("user", userDto);
                    return modelAndView;
                } else {
                    modelAndView.setViewName("404");
                    return modelAndView;
                }
            } else {
                modelAndView.setViewName("404");
                return modelAndView;
            }
        } else {
            modelAndView.setViewName("404");
            return modelAndView;
        }

    }


    @RequestMapping("/add/ajax")
    @ResponseBody
    public Response addUserAjax(@Valid @RequestBody UserValidator userValidator,
                                BindingResult result) throws Exception {


        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())
                    && getCurrentUserDetails.getDetails().getRoles().getId().equals(1)) {

                if (result.hasErrors()) {
                    return new Response("Error");
                } else {
                    User user = new User();

                    if (NotEmpty.notEmpty(userValidator.getId()))
                        user = userService.getUserById(userValidator.getId());
                    user.setEmail(userValidator.getEmail());
                    user.setName(userValidator.getName());
                    user.setPassword(PasswordEncoder.getMD5(userValidator.getPassword()));
                    user.setRoles(rolesService.getByName(userValidator.getRoles()));
                    if (user.getId() != null) {
                        userService.update(user);
                    } else if (getCurrentUserDetails.getDetails().getRoles().getId() == 1)
                        userService.add(user);

                    return new Response("Success");
                }
            }


        }
        return new Response("Error");

    }

    @RequestMapping("/list")
    @ResponseBody
    public Response userList() throws Exception {
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1) {
                return new Response(userService.userList());
            } else
                return new Response("Error");

        } else
            return new Response("Error");
    }


    @RequestMapping("/profile")
    public ModelAndView viewAndEditProfile() throws Exception {

        UserDto userDto = new UserDto();
        ModelAndView modelAndView = new ModelAndView();

        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            User user = getCurrentUserDetails.getDetails();
            if (NotEmpty.notEmpty(user)) {
                userDto.setId(user.getId());
                userDto.setName(user.getName());
                userDto.setEmail(user.getEmail());
                if (NotEmpty.notEmpty(user.getRoles()))
                    userDto.setRoles(user.getRoles().getRolename());
                if (NotEmpty.notEmpty(projectService.userProjectsList(user.getId()))) {
                    List<String> projectsList = new ArrayList<String>();
                    for (Projects project : projectService.userProjectsList(user.getId()))
                        projectsList.add(project.getId().toString());
                    userDto.setProjectses(projectsList);
                }
            }
            modelAndView.setViewName("profile");
            modelAndView.addObject("user", userDto);
            return modelAndView;
        } else {
            modelAndView.setViewName("404");
            return modelAndView;
        }
    }

    @RequestMapping("/changePassword")
    public ModelAndView changePassword() throws Exception {

        UserDto userDto = new UserDto();
        ModelAndView modelAndView = new ModelAndView();

        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            User user = getCurrentUserDetails.getDetails();
            populateUserDto(user, userDto);

            modelAndView.setViewName("changePassword");
            modelAndView.addObject("user", userDto);
            return modelAndView;
        } else {
            modelAndView.setViewName("404");
            return modelAndView;
        }
    }


    @RequestMapping("/doesUserExist")
    @ResponseBody
    public Boolean doesUserExist(@RequestParam("email") String email) throws Exception {

        return !NotEmpty.notEmpty(userService.getUserByEmail(email));

    }

    public UserDto populateUserDto(User user, UserDto userDto) {
        if (NotEmpty.notEmpty(user)) {

            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            if (NotEmpty.notEmpty(user.getRoles()))
                userDto.setRoles(user.getRoles().getRolename());

        }

        return userDto;
    }


}
