package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;

import java.util.Map;

/**
 * Created by jovin on 10/2/16.
 */
public interface UserDao {

    void add(User user)throws Exception;
    User getUserByUserName(String userName)throws Exception;
    User getUserByName(String name)throws Exception;
    Map<String,String> projectUserDropdownList()throws Exception;
}
