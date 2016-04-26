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
        Status status = new Status(1,"Open");
        User user = new User(1,null,"Jovin","jovin.25@hmail.com","123",null,null,null);
        User creator = new User(1,null,"Creator","jovin.25@hmail.com","123",null,null,null);
        User assigned = new User(1,null,"Editor","jovin.25@hmail.com","123",null,null,null);
        Set<User> users = new HashSet<User>();
        users.add(user);
        Projects project =
                new Projects(1,"TestProject","Test Description","test url",null
                        ,null,users,null,"jovin");
        Issues issue  = new Issues(1,"url","Test Issue", project, creator, assigned,
                status, tracker,null,new Date(),new Date(),null,null,new Date(), "Test description", null,null);
        Issues issue2  = new Issues(2,"url","Test Issue 2", project, creator, assigned,
                status, tracker,null,new Date(),new Date(),null,null,new Date(), "Test description", null,null);
        Attachments attachment = new Attachments();
        IssuesUpdates issuesUpdate = new IssuesUpdates(1,issue,assigned.getName(),attachment,"Fixed the issue",new Date(),null);
        attachment = new Attachments(1,null,issuesUpdate,"filelink",null,null);
        when(issueDao.getById(1)).thenReturn(issue);
        when(issueDao.getById(2)).thenReturn(issue2);
        when(issueDao.checkIfIssueExist("Test Issue")).thenReturn(true);
    }

    @Test
    public void issueGetByIdTest() throws Exception{

        Assert.assertEquals(issueService.getById(1),issueDao.getById(1));
    }

    @Test
    public void checkIfIssueExist() throws Exception {

        Assert.assertEquals(issueDao.checkIfIssueExist("Test Issue"),issueService.checkIfIssueExist("Test Issue"));
    }

    @Test
    public void projectIssuesList() throws Exception {

    }


    }
