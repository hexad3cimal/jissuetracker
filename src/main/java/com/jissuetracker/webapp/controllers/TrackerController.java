package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.services.TrackerService;
import com.jissuetracker.webapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jovin on 21/2/16.
 */
@Controller
@RequestMapping("/app/tracker")
public class TrackerController {

    @Autowired
    TrackerService trackerService;

    @RequestMapping("/list")
    @ResponseBody public Response list()throws Exception{

        return new Response(trackerService.trackerMap());
    }
}
