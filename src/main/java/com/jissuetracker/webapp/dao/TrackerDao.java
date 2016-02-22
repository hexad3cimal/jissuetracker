package com.jissuetracker.webapp.dao;

import java.util.HashMap;

/**
 * Created by jovin on 21/2/16.
 */
public interface TrackerDao {

    public HashMap<String,String> trackerDropDownMap()throws Exception;
}
