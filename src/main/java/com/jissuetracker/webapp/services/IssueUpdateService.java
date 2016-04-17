package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.IssuesUpdates;

import java.util.List;

/**
 * Created by jovin on 8/4/16.
 */
public interface IssueUpdateService {

    void addIssueUpdate(IssuesUpdates issuesUpdates)throws Exception;
    List<IssuesUpdates> issuesUpdatesList(Integer issueId)throws Exception;
}
