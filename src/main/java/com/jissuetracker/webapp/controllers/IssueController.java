package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.dao.IssueUpdateDao;
import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.*;
import com.jissuetracker.webapp.services.*;
import com.jissuetracker.webapp.utils.GetCurrentUserDetails;
import com.jissuetracker.webapp.utils.IssueUpdateDateComparator;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.jissuetracker.webapp.utils.Response;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    GetCurrentUserDetails getCurrentUserDetails;

    @Autowired
    IssueUpdateService issueUpdateService;

    //consumes the ajax post and adds Issue into database
    @RequestMapping(value = "/{project}/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addIssue(@PathVariable(value = "project") String project,
                             @RequestBody HashMap<String, String> issueMap) throws Exception {


        Issues issue = new Issues();
        Status status = new Status();
        User user = new User();
        Trackers tracker = new Trackers();
        Projects projectObject = new Projects();


        if (NotEmpty.notEmpty(issueMap)) {
            if (issueMap.containsKey("title") && !issueMap.get("title").equals(null))
                issue.setTitle(issueMap.get("title"));
            if (issueMap.containsKey("description") && !issueMap.get("description").equals(null))
                issue.setDescription(issueMap.get("description"));
            if (issueMap.containsKey("completionDate") && !issueMap.get("completionDate").equals(null))
                issue.setEndDate(new Date(issueMap.get("completionDate")));
            if (issueMap.containsKey("status") && !issueMap.get("status").equals(null)) {
                status = statusService.getById(Integer.parseInt(issueMap.get("status")));
                if (NotEmpty.notEmpty(status))
                    issue.setStatus(status);
            }
            if (issueMap.containsKey("assigned") && !issueMap.get("assigned").equals(null)) {
                user = userService.getUserById(Integer.parseInt(issueMap.get("assigned")));
                if (NotEmpty.notEmpty(user))
                    issue.setUserByAssignedToId(user);
            }
            if (issueMap.containsKey("tracker") && !issueMap.get("tracker").equals(null)) {
                tracker = trackerService.getById(Integer.parseInt(issueMap.get("tracker")));
                if (NotEmpty.notEmpty(tracker))
                    issue.setTrackers(tracker);
            }

            issue.setReadByAssigned("false");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User createdBy = userService.getUserByUserName(authentication.getName());
            if (NotEmpty.notEmpty(createdBy))
                issue.setUserByCreatedById(createdBy);

            if (NotEmpty.notEmpty(project)) {
                projectObject = projectService.getByName(project);
                if (NotEmpty.notEmpty(projectObject))
                    issue.setProjects(projectObject);
            }

            issue.setUrl("app/issues/get/" + issueService.getId());


            issueService.add(issue);
        }
        return new Response("Success");
    }

    @RequestMapping("/checkIfIssueExist")
    @ResponseBody
    public Boolean checkIfIssueExist(@RequestParam(value = "title") String title) throws Exception {

        return issueService.checkIfIssueExist(title);
    }


    @RequestMapping("/issueTitleUrlMap")
    @ResponseBody
    public Response issueTitleUrlMap(@RequestParam(value = "title") String title) throws Exception {
        return new Response(issueService.getIssueByTitleMap(title));
    }

    //retrieves the given project's issues
    @RequestMapping("/{project}")
    @ResponseBody
    public Response projectIssuesList(@PathVariable(value = "project") String project) throws Exception {
        return new Response(issueService.projectIssuesList(project));
    }


    @RequestMapping("/get/{id}")
    public String getIssueDetailsById(@PathVariable(value = "id") String id) throws Exception {

        return "issuePage";

    }

    @RequestMapping("/ajax/{id}")
    @ResponseBody
    public Response getIssueDetailsByIdAjaxCall(@PathVariable(value = "id") String id) throws Exception {

        Issues issue = issueService.getById(Integer.parseInt(id));
        Set<IssuesUpdates> issuesUpdatesSet = new TreeSet<IssuesUpdates>(new IssueUpdateDateComparator());
        if(NotEmpty.notEmpty(issue)){
            Projects project = issue.getProjects();


            if(issue.getIssuesUpdateses() != null){

            issuesUpdatesSet.addAll(issue.getIssuesUpdateses());
            issue.setIssuesUpdateses(issuesUpdatesSet);

        }


        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails()))
        {

            if((getCurrentUserDetails.getDetails().getEmail().equalsIgnoreCase(issue.getUserByAssignedToId().getEmail())) &&
                    issue.getReadByAssigned().equalsIgnoreCase("false")) {
                issue.setReadByAssigned("true");
                issueService.update(issue);
            }

            if(NotEmpty.notEmpty(project)){
                if (projectService.doesUserHasProject(getCurrentUserDetails.getDetails().getEmail()
                        , project.getName()))
                    issue.setUpdatable("true");

            }
            else
                issue.setUpdatable("false");

        }


        }

        return new Response(issue);

    }


    @RequestMapping("/updateIssue/{id}")
    @ResponseBody
    public Response addIssueUpdates(@PathVariable(value = "id") String id,
                                    @RequestBody HashMap<String,String> issueUpdates)throws Exception{
        Issues issue = new Issues();
        IssuesUpdates issuesUpdate = new IssuesUpdates();
        issuesUpdate.setDate(new Date());

        if(NotEmpty.notEmpty(id)) {
            issue = issueService.getById(Integer.parseInt(id));
            issuesUpdate.setIssues(issue);

        }

        if(NotEmpty.notEmpty(getCurrentUserDetails.getDetails())){
            issuesUpdate.setUpdatedBy(getCurrentUserDetails.getDetails().getName());
            issuesUpdate.setUpdatedByUserEmail(getCurrentUserDetails.getDetails().getEmail());
        }

        if(NotEmpty.notEmpty(issueUpdates)){
            if(issueUpdates.containsKey("updateText"))
                issuesUpdate.setUpdates(issueUpdates.get("updateText"));
        }

        issueUpdateService.addIssueUpdate(issuesUpdate);

        return new Response("Success");

    }

    @RequestMapping("/updateStatus/{id}")
    @ResponseBody
    public Response updateStatus(@PathVariable(value = "id") String id,
                                    @RequestParam(value = "value") String value)throws Exception{

        Issues issue = new Issues();
        System.out.println("Value"+value);

        if(NotEmpty.notEmpty(id)) {
            issue = issueService.getById(Integer.parseInt(id));
            if(NotEmpty.notEmpty(value)){
                 issue.setStatus(statusService.getById(Integer.parseInt(value)));
            }
            issueService.update(issue);

            return new Response("Success");
        }

        return new Response("Failure");

    }
}
