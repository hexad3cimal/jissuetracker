package com.jissuetracker.test.service;

import com.jissuetracker.webapp.dao.ProjectDao;
import com.jissuetracker.webapp.dao.ProjectDaoImpl;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.ProjectService;
import com.jissuetracker.webapp.services.ProjectServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.VoidMethodStubbable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

/**
 * Created by jovin on 24/2/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    ProjectDaoImpl projectDao;

    @InjectMocks
    ProjectServiceImpl projectService;

    @Before
    public void initialize(){

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doesUserHasProject()throws Exception{

        User user = new User(1,null,"Jovin","jovin.25@hmail.com","123",null,null,null,null);
        Set<User> users = new HashSet<User>();
        users.add(user);
        Projects project =
                new Projects(1,"TestProject","Test Description","test url",null
                        ,null,users,null,"jovin");
        when(projectDao.doesUserHasProject("TestProject","Jovin")).thenReturn(true);
        Assert.assertEquals(true,projectService.doesUserHasProject("TestProject","Jovin"));

    }

    @Test
    public void projectHomeList() throws Exception {
        User user = new User(1,null,"Jovin","jovin.25@hmail.com","123",null,null,null,null);
        Set<User> users = new HashSet<User>();
        users.add(user);
        Projects project =
                new Projects(1,"TestProject","Test Description","test url",null
                        ,null,users,null,"jovin");
        Set<User> userSet = project.getUsers();

        Assert.assertNotNull(userSet);
    }

    @Test
    public void getByName() throws Exception {

    }

    }
