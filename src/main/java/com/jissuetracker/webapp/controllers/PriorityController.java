package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.services.PriorityService;
import com.jissuetracker.webapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jovin on 13/8/16.
 */

@Controller
@RequestMapping("/app/priority")
public class PriorityController {

    @Autowired
    PriorityService priorityService;

    @RequestMapping("/list")
    @ResponseBody
    public Response statusDropDownList()throws Exception{

        return new Response(priorityService.priorityMap());
    }
}
