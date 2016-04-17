package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.IssueDao;
import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.Issues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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

    public Integer getId() throws Exception {
        return issueDao.getId();
    }

    public Boolean checkIfIssueExist(String issueTitle) throws Exception {
        return issueDao.checkIfIssueExist(issueTitle);
    }

    public HashMap<String, String> getIssueByTitleMap(String title) throws Exception {
        return issueDao.getIssueByTitleMap(title);
    }

    public List<IssueDto> projectIssuesList(String projectName) throws Exception {
        return issueDao.projectIssuesList(projectName);
    }

    public Issues getById(Integer id) throws Exception {
        return issueDao.getById(id);
    }
}
