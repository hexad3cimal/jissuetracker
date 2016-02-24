package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Trackers;

import java.util.HashMap;

/**
 * Created by jovin on 21/2/16.
 */
public interface TrackerService {

    HashMap<String,String> trackerDropDownMap()throws Exception;
    public Trackers getById(Integer trackerId)throws Exception;


}
