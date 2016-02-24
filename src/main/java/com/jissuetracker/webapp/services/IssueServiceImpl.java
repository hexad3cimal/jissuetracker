package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.IssueDao;
import com.jissuetracker.webapp.models.Issues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jovin on 24/2/16.
 */
@Transactional
@Service("IssueService")
public class IssueServiceImpl implements IssueService {

    @Autowired
    IssueDao issueDao;

    public void add(Issues issue) throws Exception {
        issueDao.add(issue);

    }

    public void update(Issues issue) throws Exception {
        issueDao.update(issue);

    }
}
