package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Issues;

import java.util.HashMap;

/**
 * Created by jovin on 24/2/16.
 */
public interface IssueService {

    public void add(Issues issue)throws Exception;
    public void update(Issues issue)throws Exception;
    public Integer getId()throws Exception;
    public Boolean checkIfIssueExist(String issueTitle)throws Exception;
    public HashMap<String,String> getIssueByTitleMap(String title)throws Exception;


}
