package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.dto.UserDto;
import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.RolesService;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/")
    public String home(){
        return "user";
    }

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

    @RequestMapping("/add")
    public ModelAndView addUserPage(){

        UserDto user = new UserDto();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addUser");
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable(value = "id")Integer id) throws Exception{

        UserDto userDto = new UserDto();
        User user = new User();
        if (id != null){
            user = userService.getUserById(id);
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            if(NotEmpty.notEmpty(user.getRoles()))
                userDto.setRoles(user.getRoles().getRolename());
            if(NotEmpty.notEmpty(user.getProjectses())){
                List<String> projectsList = new ArrayList<String>();
                for (Projects project:user.getProjectses())
                    projectsList.add(project.getId().toString());
                userDto.setProjectses(projectsList);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addUser");
        modelAndView.addObject("user",userDto);
        return modelAndView;
    }



    @RequestMapping("/addNew")
    @ResponseBody
    public Response addUserAjax(@RequestBody HashMap<String,String> userMap) throws Exception{

        User user = new User();
     if(NotEmpty.notEmpty(userMap)){
         if(userMap.containsKey("id") && NotEmpty.notEmpty(userMap.get("id")))
             user = userService.getUserById(Integer.parseInt(userMap.get("id")));
            if(userMap.containsKey("name") && NotEmpty.notEmpty(userMap.get("name")))
                user.setName(userMap.get("name"));
            if (userMap.containsKey("password") && NotEmpty.notEmpty(userMap.get("password")))
                user.setPassword(com.jissuetracker.webapp.utils.PasswordEncoder.getMD5(userMap.get("password")));
            if (userMap.containsKey("role") && NotEmpty.notEmpty(userMap.get("role")))
                user.setRoles(rolesService.getByName(userMap.get("role")));
            if (userMap.containsKey("email") && NotEmpty.notEmpty(userMap.get("email")))
                  user.setEmail(userMap.get("email"));
         if(user.getId() !=null){
             System.out.println("Reached output");
             userService.update(user);
         }else
            userService.add(user);

         return new Response("Success");

     }
        return new Response("Error");

    }

    @RequestMapping("/list")
    @ResponseBody
    public Response userList()throws Exception{

        return new Response(userService.userList());
    }
}
