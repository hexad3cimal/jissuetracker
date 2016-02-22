package com.jissuetracker.webapp.dao;

import java.util.HashMap;

/**
 * Created by jovin on 22/2/16.
 */
public interface StatusDao {

    public HashMap<String,String> statusDropDownMap()throws Exception;
}
