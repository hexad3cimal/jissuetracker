package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.models.*;
import com.jissuetracker.webapp.services.*;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by jovin on 24/2/16.
 */
@Controller
@RequestMapping(value = "/app/issues")
public class IssueController {

    @Autowired
    StatusService statusService;

    @Autowired
    UserService userService;

    @Autowired
    TrackerService trackerService;

    @Autowired
    IssueService issueService;

    @Autowired
    ProjectService projectService;

    @Autowired
    SessionFactory sessionFactory;

    @RequestMapping(value = "/{project}/add",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public Response addIssue(@PathVariable(value = "project") String project,
            @RequestBody HashMap<String,String> issueMap)throws Exception{


        Issues issue = new Issues();
        Status status = new Status();
        User user = new User();
        Trackers tracker = new Trackers();
        Projects projectObject = new Projects();


        if(NotEmpty.notEmpty(issueMap)){
            if (issueMap.containsKey("title") && !issueMap.get("title").equals(null))
                issue.setTitle(issueMap.get("title"));
            if (issueMap.containsKey("description") && !issueMap.get("description").equals(null))
                issue.setDescription(issueMap.get("description"));
            if (issueMap.containsKey("completionDate") && !issueMap.get("completionDate").equals(null))
                issue.setEndDate(new Date(issueMap.get("completionDate")));
            if (issueMap.containsKey("status") && !issueMap.get("status").equals(null)){
                status = statusService.getById(Integer.parseInt(issueMap.get("status")));
                if (NotEmpty.notEmpty(status))
                    issue.setStatus(status);
            }
            if (issueMap.containsKey("assigned") && !issueMap.get("assigned").equals(null)){
                user = userService.getUserById(Integer.parseInt(issueMap.get("assigned")));
                if (NotEmpty.notEmpty(user))
                    issue.setUserByAssignedToId(user);
            }
            if (issueMap.containsKey("tracker") && !issueMap.get("tracker").equals(null)){
                tracker = trackerService.getById(Integer.parseInt(issueMap.get("tracker")));
                if (NotEmpty.notEmpty(tracker))
                    issue.setTrackers(tracker);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User createdBy = userService.getUserByUserName(authentication.getName());
            if (NotEmpty.notEmpty(createdBy))
                issue.setUserByCreatedById(createdBy);

            if (NotEmpty.notEmpty(project)){
                projectObject=projectService.getByName(project);
                if(NotEmpty.notEmpty(projectObject))
                    issue.setProjects(projectObject);
            }

            issue.setUrl("/app/issues/"+ issueService.getId());



                issueService.add(issue);
        }
        return new Response("Success");
    }

    @RequestMapping("/checkIfIssueExist")
    @ResponseBody
    public Boolean checkIfIssueExist(@RequestParam (value = "title")String title)throws Exception{

        System.out.println("Title>>>>"+title);
        return issueService.checkIfIssueExist(title);
    }


    @RequestMapping("/issueTitleUrlMap")
    @ResponseBody
    public Response issueTitleUrlMap(@RequestParam(value = "title")String title)throws Exception{

        return new Response(issueService.getIssueByTitleMap(title));
    }
}
