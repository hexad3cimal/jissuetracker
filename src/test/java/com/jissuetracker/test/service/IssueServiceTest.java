package com.jissuetracker.test.service;

import com.jissuetracker.webapp.dao.IssueDaoImpl;
import com.jissuetracker.webapp.services.IssueServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by jovin on 24/2/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    @Mock
    IssueDaoImpl issueDao;

    @InjectMocks
    IssueServiceImpl issueService;

    @Before
    public void initialize(){

        MockitoAnnotations.initMocks(this);
    }


}
