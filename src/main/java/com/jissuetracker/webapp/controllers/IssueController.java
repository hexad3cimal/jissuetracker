package com.jissuetracker.webapp.controllers;

import com.jissuetracker.webapp.models.*;
import com.jissuetracker.webapp.services.*;
import com.jissuetracker.webapp.utils.*;
import com.jissuetracker.webapp.validators.IssueUpdateValidator;
import com.jissuetracker.webapp.validators.IssueValidator;
import org.apache.commons.io.IOUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

    @Autowired
    Environment environment;

    @Autowired
    PriorityService priorityService;

    @Autowired
    AttachmentService attachmentService;


    //consumes the ajax post and adds Issue into database
    /**
     * @param project - name of the project issue to be added
     * @param issueValidator - validator object for checking errors
     * @return Hashmap containing operation status
     */
    @RequestMapping(value = "/{project}/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addIssue(@PathVariable(value = "project") String project,
                             @Valid @RequestBody IssueValidator issueValidator,
                             BindingResult result) throws Exception {

        Issues issue = new Issues();
        Status status = new Status();
        User user = new User();
        Trackers tracker = new Trackers();

        HashMap<String, String> responseMap = new HashMap<String, String>();


        if (result.hasErrors()) {
            responseMap.put("status", "Error");
            return new Response(responseMap);
        } else {
            issue.setTitle(XssCleaner.clean(issueValidator.getTitle()));

            issue.setDescription(XssCleaner.clean(issueValidator.getDescription()));
            issue.setEndDate(new Date(issueValidator.getCompletionDate()));
            status = statusService.getById(issueValidator.getStatus());
            if (NotEmpty.notEmpty(status))
                issue.setStatus(status);

            user = userService.getUserById(issueValidator.getAssigned());
            if (NotEmpty.notEmpty(user))
                issue.setUserByAssignedToId(user);

            tracker = trackerService.getById(issueValidator.getTracker());
            if (NotEmpty.notEmpty(tracker))
                issue.setTrackers(tracker);


            Priority priority = priorityService.getByPriorityId(issueValidator.getPriority());
            if (NotEmpty.notEmpty(priority))
                issue.setPriority(priority);

            issue.setReadByAssigned("false");
            if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails()))
                issue.setUserByCreatedById(getCurrentUserDetails.getDetails());

            if (NotEmpty.notEmpty(project)) {
                Projects projectObject = projectService.getByProjectNameAlongWithIssuesAndUsers(project);
                if (NotEmpty.notEmpty(projectObject))
                    issue.setProjects(projectObject);
            }
            issue.setUpdatedOn(new Date());
            issue.setCreatedTimeStamp(new Date());
            issueService.add(issue);
            issue.setUrl("app/issues/get/" + issue.getId());
            issueService.update(issue);


            List<String> filesList = issueValidator.getFiles();
            if (NotEmpty.notEmpty(filesList)) {
                for (String s : filesList) {
                    if (s.contains("&")) {
                        Attachments attachment = new Attachments();
                        System.out.println(s);
                        String fileLink[] = s.split("&");
                        attachment.setIssues(issue);
                        attachment.setLink(fileLink[1]);
                        attachment.setOriginalName(fileLink[0]);
                        attachmentService.add(attachment);
                    }
                }
            }
            responseMap.put("status", "Success");
            responseMap.put("issueId", issue.getId().toString());
            return new Response(responseMap);

        }

    }

    //check whether an issue with given title exists
    //@param issueTitle   the title of the entered issue from ui.
    @RequestMapping("/checkIfIssueExist")
    @ResponseBody
    public Boolean checkIfIssueExist(@RequestParam(value = "title") String issueTitle) throws Exception {
        return issueService.checkIfIssueExist(issueTitle);
    }


    //retrieves the given project's issues
    @RequestMapping("/{project}")
    @ResponseBody
    public Response projectIssuesList(@PathVariable(value = "project") String project) throws Exception {
        return new Response(issueService.projectIssuesList(project));
    }

    //retrieves the given project's issues created by logged in user
    @RequestMapping("/created/{projectName}")
    @ResponseBody
    public Response projectIssuesListCreatedByLoggedInUser(@PathVariable(value = "projectName") String projectName) throws Exception {

        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails()))
            return new Response(issueService.projectIssuesCreatedByLoggedInUserList(projectName, getCurrentUserDetails.getDetails()));

        return new Response("Error");
    }


    //retrieves the given project's issues assigned to logged in user
    @RequestMapping("/assigned/{projectName}")
    @ResponseBody
    public Response projectIssuesListAssignedToLoggedInUser(@PathVariable(value = "projectName") String projectName) throws Exception {
        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails()))
            return new Response(issueService.projectIssuesAssignedToLoggedInUserList(projectName, getCurrentUserDetails.getDetails()));

        return new Response("Error");
    }


    //retrieves the given project's issues based on status
    @RequestMapping("/filtered/{projectName}/{mode}/{status}")
    @ResponseBody
    public Response openProjectIssuesList(@PathVariable(value = "projectName") String projectName,
                                          @PathVariable(value = "mode") String mode,
                                          @PathVariable(value = "status") String status) throws Exception {


        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            if (mode.equalsIgnoreCase("all-issues"))
                return new Response(issueService.projectIssuesListBasedOnStatus(projectName, status));
            if (mode.equalsIgnoreCase("issues-assigned-to-you"))
                return new Response(issueService.projectIssuesListBasedOnStatusAssignedToLoggedInUserList(projectName, getCurrentUserDetails.getDetails(), status));
            if (mode.equalsIgnoreCase("issues-by-you"))
                return new Response(issueService.projectIssuesListBasedOnStatusCreatedByLoggedInUserList(projectName, getCurrentUserDetails.getDetails(), status));
        }

        return new Response("Error");
    }

    //retrieves the given project's issues based on tracker
    /**@RequestMapping @params
     * @param projectName - name of the project
     * @param mode - specifies to search whether the issues created-by/assigned-to is to be looked
     * @param tracker - issue tracker to be searched
     * @param status - issue status to be searched
     * @return JSON response based on the above params
     */
    @RequestMapping("/filtered/{projectName}/{mode}/{status}/{tracker}")
    @ResponseBody
    public Response projectIssuesListBasedOnType(@PathVariable(value = "projectName") String projectName,
                                                 @PathVariable(value = "mode") String mode,
                                                 @PathVariable(value = "tracker") String tracker,
                                                 @PathVariable(value = "status") String status) throws Exception {

        if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            if (mode.equalsIgnoreCase("all-issues"))
                return new Response(issueService.projectIssuesListBasedOnTracker(projectName, status, tracker));
            if (mode.equalsIgnoreCase("issues-assigned-to-you"))
                return new Response(issueService.projectIssuesListBasedOnTrackerAssignedToLoggedInUserList(projectName, getCurrentUserDetails.getDetails(), status, tracker));
            if (mode.equalsIgnoreCase("issues-by-you"))
                return new Response(issueService.projectIssuesListBasedOnTrackerCreatedByLoggedInUserList(projectName, getCurrentUserDetails.getDetails(), status, tracker));
        }

        return new Response("Error");
    }

    //serves the issuePage
    @RequestMapping("/get/{issueId}")
    public String getIssueDetailsById(@PathVariable(value = "issueId") Integer issueId) throws Exception {
        Issues issue = issueService.getByIdWithUpdatesStatusTrackerPriorityAttachments(issueId);

        if (NotEmpty.notEmpty(issue)) {

            if (projectService.doesUserHasProject(getCurrentUserDetails.getDetails().getEmail()
                    , issue.getProjects().getName()) ||
                    new Integer(1).equals(getCurrentUserDetails.getDetails().getRoles().getId())) {

                return "issuePageNew";
            } else
                return "issuePageViewOnly";
        }

        return "404";

    }


    //serves the issuePage details via ajax
    @RequestMapping("/ajax/{issueId}")
    @ResponseBody
    public Response getIssueDetailsByIdAjaxCall(@PathVariable(value = "issueId") Integer issueId) throws Exception {

        String html = "";
        String priorityHtml = "";
        String statusHtml = "";
        String trackerHtml = "";
        String memberHtml = "";

        Issues issue = issueService.getByIdWithUpdatesStatusTrackerPriorityAttachments(issueId);
        Set<IssuesUpdates> issuesUpdatesSet = new TreeSet<IssuesUpdates>(new IssueUpdateDateComparator());
        if (NotEmpty.notEmpty(issue)) {

            if (NotEmpty.notEmpty(issue.getPriority())) {

                if (issue.getPriority().getName().equalsIgnoreCase("High"))
                    priorityHtml = " <dd><span class=\"label label-danger\">High</span></dd>";
                else if (issue.getPriority().getName().equalsIgnoreCase("Medium"))
                    priorityHtml = " <dd><span class=\"label label-warning\">Medium</span></dd>";
                else
                    priorityHtml = " <dd><span class=\"label label-info\">Low</span></dd>";
            }


            if (NotEmpty.notEmpty(issue.getUserByAssignedToId())) {

                memberHtml = memberHtml +
                        " <li><img alt=\"Avatar\" class=\"img-circle\"\n" +
                        "  src=\"" + issue.getUserByAssignedToId().getProfilePic() + "+\">\n" +
                        " <p><strong>" + issue.getUserByAssignedToId().getName() + "</strong></p>" +
                        " </li>\n";

            }
            if (NotEmpty.notEmpty(issue.getUserByCreatedById())) {

                memberHtml = memberHtml + "<li><img alt=\"Avatar\" class=\"img-circle\"\n" +
                        "src=\"" + issue.getUserByCreatedById().getProfilePic() + "+\">\n" +
                        " <p><strong>" + issue.getUserByCreatedById().getName() + "</strong></p>" +
                        " </li>\n";
            }


            if (NotEmpty.notEmpty(issue.getTrackers())) {

                if (issue.getTrackers().getName().equalsIgnoreCase("Bug"))
                    trackerHtml = " <dd><span class=\"label label-danger\">Bug</span></dd>";
                else if (issue.getTrackers().getName().equalsIgnoreCase("Feature"))
                    trackerHtml = " <dd><span class=\"label label-warning\">Feature</span></dd>";
                else
                    trackerHtml = " <dd><span class=\"label label-info\">Modification</span></dd>";
            }


            if (NotEmpty.notEmpty(issue.getStatus())) {

                if (issue.getStatus().getName().equalsIgnoreCase("Closed"))
                    statusHtml = " <dd><span id=\"issueStatus\" class=\"label label-danger\">Closed</span></dd>";
                else
                    statusHtml = " <dd><span id=\"issueStatus\" class=\"label label-success\">Open</span></dd>";
            }

            html = "  <div class=\"primary-content-heading clearfix\"><h2>" + issue.getTitle() + "</h2>\n" +
                    "        <hr style=\"border:1px solid #fff\">\n" +
                    "    </div>\n" +
                    "    <div class=\"row\">\n" +
                    "        <div class=\"col-md-8\">\n" +
                    "            <div class=\"project-section general-info\">\n" +
                    "                 <p>" + issue.getDescription() + "</p>\n" +
                    "                <div class=\"row\">\n" +
                    "                    <div class=\"col-sm-12\">\n" +
                    "                        <dl class=\"dl-horizontal\">\n" +
                    "                            <dt>Date:</dt>\n" +
                    "                            <dd>" + issue.getCreatedTimeStamp() + " - " + issue.getEndDate() + "</dd>\n" +
                    "                           <dt>Priority:</dt>\n" + priorityHtml +
                    "                            <dt>Status:</dt>\n" + statusHtml +
                    "                            <dt>Tracker:</dt>\n" + trackerHtml +
                    "                            <dt>Members:</dt>\n" +
                    "                            <dd>" + "<ul class=\"list-inline team-list\">\n" +
                    memberHtml + "   </ul>\n" +
                    "                            </dd>\n" +
                    "                        </dl>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>" +
                    "   <div class=\"project-section activity\"><h3>Activity</h3>\n" +
                    "                <ul class=\"list-unstyled project-activity-list\">";

            String updateHtml = "";
            String attachmentHtml = "";
            if (issue.getIssuesUpdateses() != null) {
                issuesUpdatesSet.addAll(issue.getIssuesUpdateses());
                for (IssuesUpdates issuesUpdates : issuesUpdatesSet) {
                    String updatesAttachmentHtml = "";
                    if (NotEmpty.notEmpty(issuesUpdates.getAttachmentses())) {

                        for (Attachments attachments : issuesUpdates.getAttachmentses()) {

                            updatesAttachmentHtml = updatesAttachmentHtml +
                                    "<p><a href=\"/jit/app/issues/files/" + attachments.getId() + "\">" + attachments.getOriginalName() + "</a><p>";

                        }
                    }

                    updateHtml = updateHtml + "<li>\n" +
                            " <div class=\"media activity-item\">" +
                            "<div class=\"media-body\"><p class=\"activity-title\">" + issuesUpdates.getUpdatedByUserFullName() + " has posted an update \n" +
                            " </p>\n" +
                            " <small class=\"text-muted\">" + issuesUpdates.getCreatedTimeStamp() + "</small>\n" +
                            " <div class=\"activity-attachment\">\n" +
                            " <div class=\"well well-sm\"><strong>Update Note:</strong>\n" +
                            " <p>" + issuesUpdates.getUpdates() + "</p>" + updatesAttachmentHtml + "</div>" +

                            " </div>\n" +
                            " </div>\n" +
                            "</div>\n" +
                            "</li>";

                }

            }

            if (NotEmpty.notEmpty(issue.getAttachmentses())) {

                attachmentHtml = "<div class=\"col-md-4\">\n" +
                        "            <div class=\"widget\">\n" +
                        "                <div class=\"widget-header clearfix\"><h3><i class=\"fa fa-document\"></i> <span>RECENT FILES</span></h3>\n" +
                        "                </div>\n" +
                        "                <div class=\"widget-content\" style=\"word-wrap: break-word;\">\n" +
                        "                    <ul class=\"fa-ul recent-file-list bottom-30px\">";

                for (Attachments attachments : issue.getAttachmentses()) {

                    attachmentHtml = attachmentHtml
                            + "<li><i class=\"fa-li fa fa-file-zip-o\"></i>" +
                            "<a href=\"/jit/app/issues/files/" + attachments.getId() + "\">" + attachments.getOriginalName() + "</a></li>\n";

                }

                attachmentHtml = attachmentHtml + "</ul>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "\n" +
                        "    </div>";
            }


            html = html + updateHtml + "  </ul>\n" +
                    "            </div>\n" +
                    attachmentHtml +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>";


            if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
                if ((getCurrentUserDetails.getDetails().getEmail().equalsIgnoreCase(issue.getUserByAssignedToId().getEmail())) &&
                        issue.getReadByAssigned().equalsIgnoreCase("false")) {
                    issue.setReadByAssigned("true");
                    issueService.update(issue);
                }
            }
        }

        return new Response(html);

    }


    //updates the issue
    @RequestMapping("/updateIssue/{issueId}")
    @ResponseBody
    public Response addIssueUpdates(@PathVariable(value = "issueId") Integer issueId,
                                    @Valid @RequestBody IssueUpdateValidator issueUpdateValidator, BindingResult result) throws Exception {


        if (result.hasErrors()) {
            return new Response("Error");
        } else {
            Issues issue = new Issues();
            IssuesUpdates issuesUpdate = new IssuesUpdates();
            issuesUpdate.setCreatedTimeStamp(new Date());
            issue = issueService.getByIdWithUpdatesStatusTrackerPriorityAttachments(issueId);

            if (NotEmpty.notEmpty(issue)) {
                issuesUpdate.setIssues(issue);
            }

            if (NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
                issuesUpdate.setUpdatedByUserFullName(getCurrentUserDetails.getDetails().getName());
                issuesUpdate.setUser(getCurrentUserDetails.getDetails());
            }

            issuesUpdate.setUpdates(XssCleaner.clean(issueUpdateValidator.getUpdateText()));


            issuesUpdate.setCreatedTimeStamp(new Date());
            issueUpdateService.addIssueUpdate(issuesUpdate);

            List<String> filesList = issueUpdateValidator.getFiles();
            if (NotEmpty.notEmpty(filesList)) {
                for (String s : filesList) {
                    if (s.contains("&")) {
                        Attachments attachment = new Attachments();
                        System.out.println(s);
                        String fileLink[] = s.split("&");
                        attachment.setIssuesUpdates(issuesUpdate);
                        attachment.setLink(fileLink[1]);
                        attachment.setOriginalName(fileLink[0]);
                        attachmentService.add(attachment);
                    }
                }
            }
        }
        return new Response("Success");

    }


    //consumes jquery editable request and updates the status of the issue
    @RequestMapping("/updateStatus/{issueId}")
    @ResponseBody
    public Response updateStatus(@PathVariable(value = "issueId") Integer issueId,
                                 @RequestParam(value = "value") Integer value) throws Exception {
        if (NotEmpty.notEmpty(issueId)) {
            Issues issue = issueService.getByIdWithUpdatesStatusTrackerPriorityAttachments(issueId);
            if (NotEmpty.notEmpty(value) && NotEmpty.notEmpty(issue)) {
                Status status = statusService.getById(value);
                issue.setStatus(status);
                IssuesUpdates issuesUpdates = new IssuesUpdates();
                issuesUpdates.setIssues(issue);
                issuesUpdates.setUser(getCurrentUserDetails.getDetails());
                issuesUpdates.setCreatedTimeStamp(new Date());
                issuesUpdates.setUpdatedByUserFullName(getCurrentUserDetails.getDetails().getName());
                issuesUpdates.setUpdates(getCurrentUserDetails.getDetails().getName() +
                        " updated the status to " + status.getName());
                issueService.update(issue);
                return new Response("Success");
            }

        }
        return new Response("Failure");

    }


    //handles upload request from ui and saves to the location specified in property file
    @RequestMapping(value = "/messageFiles", method = RequestMethod.POST)
    public
    @ResponseBody
    Response doUploadMessageFiles(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        HashMap<String, String> uploadDetails = new HashMap<String, String>();
        Integer uploadedFiles = (Integer) request.getSession().getAttribute("messageFiles");
        if (!file.isEmpty() && NotEmpty.notEmpty(getCurrentUserDetails.getDetails()) && uploadedFiles < 3
                ) {
            try {

                if (NotEmpty.notEmpty(file.getOriginalFilename())) {
                    if (file.getOriginalFilename().split("\\.")[1].equalsIgnoreCase("png") ||
                            file.getOriginalFilename().split("\\.")[1].equalsIgnoreCase("pdf") ||
                            file.getOriginalFilename().split("\\.")[1].equalsIgnoreCase("jpg") ||
                            file.getOriginalFilename().split("\\.")[1].equalsIgnoreCase("jpeg") ||
                            file.getOriginalFilename().split("\\.")[1].equalsIgnoreCase("doc") ||
                            file.getOriginalFilename().split("\\.")[1].equalsIgnoreCase("docx")
                            ) {

                        String uploadsDir = environment.getProperty("paths.uploadedFiles");
                        String filePath = uploadsDir + "/" + getCurrentUserDetails.getDetails().getId() + new Date().toString().replace(" ", "") + "." + file.getOriginalFilename().split("\\.")[1];
                        File dest = new File(filePath);
                        file.transferTo(dest);

                        uploadDetails.put(file.getOriginalFilename(), filePath);
                        uploadedFiles++;
                        request.getSession().setAttribute("messageFiles", uploadedFiles);
                        return new Response(uploadDetails);
                    } else
                        return new Response("file type not supported");

                }


            } catch (Exception e) {
                e.printStackTrace();
                uploadDetails.put("Error", "Error occurred during upload" + uploadedFiles);
            }
        } else if (uploadedFiles.equals(2)) {
            uploadDetails.put("Limit Reached", "Only 3 files per message is allowed");
        }
        uploadDetails.put("Error", "Error occurred during upload" + uploadedFiles);
        return new Response(uploadDetails);
    }

    //delete the uploaded file
    @RequestMapping(value = "/deleteFile", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public Response deleteFile(@RequestBody HashMap<String, String> fileLocation,
                               HttpServletRequest request) throws Exception {


        if (fileLocation.containsKey("fileLocation") && NotEmpty.notEmpty(fileLocation.get("fileLocation"))) {
            if (fileLocation.get("fileLocation").contains("&")) {
                File messageFile = new File(fileLocation.get("fileLocation").replace(" ", "").split("&")[1]);
                if (messageFile != null) {
                    Integer uploadedFiles = (Integer) request.getSession().getAttribute("messageFiles");
                    messageFile.delete();
                    uploadedFiles--;
                    request.getSession().setAttribute("messageFiles", uploadedFiles);
                    return new Response("Deleted" + uploadedFiles);
                } else
                    return new Response("File doesn't exist");

            }

        }

        return new Response("File doestn't exist");

    }

    //get uploaded files
    @RequestMapping(value = "/files/{attachmentId}")
    public void getFile(@PathVariable("attachmentId") Integer attachmentId, HttpServletResponse response) throws Exception {
        Attachments attachment = attachmentService.getByIdWithIssuesAndIssueUpdates(attachmentId);
        if (NotEmpty.notEmpty(attachment)) {
            Issues issues = attachment.getIssues();
            IssuesUpdates issuesUpdates = attachment.getIssuesUpdates();
            if (NotEmpty.notEmpty(issues)) {
                try {
                    processDownload(attachment, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (NotEmpty.notEmpty(issuesUpdates)) {
                processDownload(attachment, response);
            }
        }
    }


    //function for processing download requests
    public void processDownload(Attachments attachment, HttpServletResponse response) throws Exception {
        try {
            File fileToDownload = new File(attachment.getLink());
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getOriginalName());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
