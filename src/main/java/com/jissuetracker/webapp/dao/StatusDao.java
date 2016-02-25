package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Status;

import java.util.HashMap;

/**
 * Created by jovin on 22/2/16.
 */
public interface StatusDao {

    HashMap<String,String> statusDropDownMap()throws Exception;
    public Status getById(Integer statusId)throws Exception;
    public void add(Status status)throws Exception;
    public void update(Status status)throws Exception;
}
