package com.jissuetracker.test.service;

import com.jissuetracker.webapp.dao.IssueDaoImpl;
import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.*;
import com.jissuetracker.webapp.services.IssueServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * Created by jovin on 8/4/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueTest {

    @Mock
    IssueDaoImpl issueDao;

    @InjectMocks
    IssueServiceImpl issueService;

    @Before
    public void initialize() throws Exception{

        MockitoAnnotations.initMocks(IssueTest.class);
        Trackers tracker = new Trackers(1,"Bug");
        Priority priority = new Priority("High");
        Roles role = new Roles("Adminstrator");
        Status status = new Status(1,"Open");
        User user = new User(role,"Jovin","jovin.25@hmail.com","123");
        User creator = new User(role,"Creater","creator@gmail.com","123");
        User assigned = new User(role,"Assigned","assigned@gmail.com","123");
        Set<User> users = new HashSet<User>();
        users.add(user);
        Projects project =
                new Projects(1,"TestProject","Test Description","test url",null
                        ,null,users,null,"jovin");
        Issues issue  = new Issues(priority,project, creator, assigned,
                status, tracker,new Date(), "Test description", "Test title");

        Attachments attachment = new Attachments();
        IssuesUpdates issuesUpdate = new IssuesUpdates(issue,assigned,"Fixed the issue");
        attachment = new Attachments(null,issuesUpdate,"filelink",null);
        when(issueDao.getByIdWithUpdatesStatusTrackerPriorityAttachments(1)).thenReturn(issue);
        when(issueDao.checkIfIssueExist("Test Issue")).thenReturn(true);
    }

    @Test
    public void issueGetByIdTest() throws Exception{

        Assert.assertEquals(issueService.getUserIssuesByUserId(1),issueDao.getUserIssuesByUserId(1));
    }

    @Test
    public void checkIfIssueExist() throws Exception {

        Assert.assertEquals(issueDao.checkIfIssueExist("Test Issue"),issueService.checkIfIssueExist("Test Issue"));
    }

    @Test
    public void projectIssuesList() throws Exception {

    }


    }
