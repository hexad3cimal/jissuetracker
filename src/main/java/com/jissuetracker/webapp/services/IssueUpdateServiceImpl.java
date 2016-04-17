package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.IssueUpdateDao;
import com.jissuetracker.webapp.models.IssuesUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jovin on 8/4/16.
 */
@Service("IssueUpdateService")
@Transactional
public class IssueUpdateServiceImpl implements IssueUpdateService {

    @Autowired
    IssueUpdateDao issueUpdateDao;

    public void addIssueUpdate(IssuesUpdates issuesUpdates) throws Exception {
        issueUpdateDao.addIssueUpdate(issuesUpdates);
    }

    public List<IssuesUpdates> issuesUpdatesList(Integer issueId) throws Exception {
        return issueUpdateDao.issuesUpdatesList(issueId);
    }
}
