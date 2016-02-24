package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Issues;

/**
 * Created by jovin on 24/2/16.
 */
public interface IssueDao {

    public void add(Issues issue)throws Exception;
    public void update(Issues issue)throws Exception;
}
