package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.utils.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by jovin on 24/2/16.
 */
@Controller
@RequestMapping(value = "/app/issues")
public class IssueController {

    @RequestMapping(value = "/add",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public Response addIssue(@RequestBody HashMap<String,Object> issueMap){
        return new Response("null");
    }
}
