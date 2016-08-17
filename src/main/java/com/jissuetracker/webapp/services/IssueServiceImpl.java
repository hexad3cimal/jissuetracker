package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.IssueDao;
import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jovin on 24/2/16.
 */
@Service("IssueService")
public class IssueServiceImpl implements IssueService {

    @Autowired
    IssueDao issueDao;

    @Transactional
    public void add(Issues issue) throws Exception {
        issueDao.add(issue);

    }

    @Transactional
    public void update(Issues issue) throws Exception {
        issueDao.update(issue);

    }

    @Transactional(readOnly = true)
    public Integer getId() throws Exception {
        return issueDao.getId();
    }

    @Transactional(readOnly = true)
    public Boolean checkIfIssueExist(String issueTitle) throws Exception {
        return issueDao.checkIfIssueExist(issueTitle);
    }



    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesList(String projectName) throws Exception {
        return issueDao.projectIssuesList(projectName);
    }

    @Transactional(readOnly = true)
    public Issues getByIdWithUpdatesStatusTrackerPriorityAttachments(Integer id) throws Exception {
        return issueDao.getByIdWithUpdatesStatusTrackerPriorityAttachments(id);
    }

    @Transactional(readOnly = true)
    public List<Issues> getUserIssuesByUserId(Integer userId) throws Exception {
        return issueDao.getUserIssuesByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Boolean checkIfIssueExist(Integer issueId) throws Exception {
        return issueDao.checkIfIssueExist(issueId);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesCreatedByLoggedInUserList(String projectName, User user) throws Exception {
        return issueDao.projectIssuesCreatedByLoggedInUserList(projectName,user);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesAssignedToLoggedInUserList(String projectName, User user) throws Exception {
        return issueDao.projectIssuesAssignedToLoggedInUserList(projectName,user);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesListBasedOnStatus(String projectName, String status) throws Exception {
        return issueDao.projectIssuesListBasedOnStatus(projectName,status);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesListBasedOnStatusCreatedByLoggedInUserList(String projectName, User user, String status) throws Exception {
        return issueDao.projectIssuesListBasedOnStatusCreatedByLoggedInUserList(projectName,user,status);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesListBasedOnStatusAssignedToLoggedInUserList(String projectName, User user, String status) throws Exception {
        return issueDao.projectIssuesListBasedOnStatusAssignedToLoggedInUserList(projectName,user,status);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesListBasedOnTracker(String projectName,String status, String tracker) throws Exception {
        return issueDao.projectIssuesListBasedOnTracker(projectName,status,tracker);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesListBasedOnTrackerCreatedByLoggedInUserList(String projectName, User user, String status, String tracker) throws Exception {
        return issueDao.projectIssuesListBasedOnTrackerCreatedByLoggedInUserList(projectName,user,status,tracker);
    }

    @Transactional(readOnly = true)
    public List<IssueDto> projectIssuesListBasedOnTrackerAssignedToLoggedInUserList(String projectName, User user, String status, String tracker) throws Exception {
        return issueDao.projectIssuesListBasedOnTrackerAssignedToLoggedInUserList(projectName,user,status,tracker);
    }


}
