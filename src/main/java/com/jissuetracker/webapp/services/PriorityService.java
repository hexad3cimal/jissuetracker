package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Priority;

import java.util.HashMap;

/**
 * Created by jovin on 13/8/16.
 */
public interface PriorityService {

    void add(Priority priority)throws Exception;
    HashMap<Integer,String> priorityMap()throws Exception;
    Priority getByPriorityId(Integer priorityId)throws Exception;
}
