package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Status;

import java.util.HashMap;

/**
 * Created by jovin on 22/2/16.
 */
public interface StatusDao {

    HashMap<Integer,String> statusDropDownMap()throws Exception;
    Status getById(Integer statusId)throws Exception;
    void add(Status status)throws Exception;
    void update(Status status)throws Exception;
}
