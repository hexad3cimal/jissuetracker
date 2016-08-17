package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 24/2/16.
 */
public interface IssueDao {

    void add(Issues issue)throws Exception;
    void update(Issues issue)throws Exception;
    Integer getId()throws Exception;
    Boolean checkIfIssueExist(String issueTitle)throws Exception;
    Boolean checkIfIssueExist(Integer issueTitle)throws Exception;
    List<IssueDto> projectIssuesList(String projectName)throws Exception;
    List<IssueDto> projectIssuesCreatedByLoggedInUserList(String projectName, User user)throws Exception;
    List<IssueDto> projectIssuesAssignedToLoggedInUserList(String projectName, User user)throws Exception;
    List<IssueDto> projectIssuesListBasedOnStatus(String projectName, String status)throws Exception;
    List<IssueDto> projectIssuesListBasedOnStatusCreatedByLoggedInUserList(String projectName, User user, String status)throws Exception;
    List<IssueDto> projectIssuesListBasedOnStatusAssignedToLoggedInUserList(String projectName, User user, String status)throws Exception;
    List<IssueDto> projectIssuesListBasedOnTracker(String projectName,String status,String tracker)throws Exception;
    List<IssueDto> projectIssuesListBasedOnTrackerCreatedByLoggedInUserList(String projectName, User user, String status, String tracker)throws Exception;
    List<IssueDto> projectIssuesListBasedOnTrackerAssignedToLoggedInUserList(String projectName, User user, String status, String tracker)throws Exception;
    Issues getByIdWithUpdatesStatusTrackerPriorityAttachments(Integer id)throws Exception;
    List<Issues> getUserIssuesByUserId(Integer userId)throws Exception;
    }
