package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Issues;

/**
 * Created by jovin on 24/2/16.
 */
public interface IssueService {

    public void add(Issues issue)throws Exception;
    public void update(Issues issue)throws Exception;
}
