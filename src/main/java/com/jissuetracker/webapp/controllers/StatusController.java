package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.services.StatusService;
import com.jissuetracker.webapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jovin on 22/2/16.
 */
@Controller
@RequestMapping("/app/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @RequestMapping("/list")
    @ResponseBody
    public Response statusDropDownList()throws Exception{

        return new Response(statusService.statusDropDownMap());
    }
}
