package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.IssuesUpdates;

import java.util.List;

/**
 * Created by jovin on 7/4/16.
 */
public interface IssueUpdateDao {

    void addIssueUpdate(IssuesUpdates issuesUpdates)throws Exception;
    List<IssuesUpdates> issuesUpdatesList(Integer issueId)throws Exception;

}
