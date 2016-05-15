package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.models.Roles;
import com.jissuetracker.webapp.services.RolesService;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jovin on 21/4/16.
 */

@Controller
@RequestMapping(value = "/app/roles")
public class RoleController {

    @Autowired
    RolesService rolesService;

    //handles and produce roles listing
    @RequestMapping(value = "/list")
    @ResponseBody
    public Response listRoles()throws Exception{

        List<Roles> rolesList = rolesService.rolesList();
        Map<String,String> rolesMap = new HashMap<String,String>();

        if(NotEmpty.notEmpty(rolesList)){
            for (Roles role : rolesList)
                rolesMap.put(role.getRolename(),role.getRolename());

        }
        return new Response(rolesMap);

    }

}
