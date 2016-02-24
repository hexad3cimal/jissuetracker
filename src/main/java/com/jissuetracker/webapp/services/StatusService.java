package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Status;

import java.util.HashMap;

/**
 * Created by jovin on 22/2/16.
 */
public interface StatusService {
    HashMap<String,String> statusDropDownMap()throws Exception;
    public Status getById(String statusId)throws Exception;
    public void add(Status status)throws Exception;
    public void update(Status status)throws Exception;
}
