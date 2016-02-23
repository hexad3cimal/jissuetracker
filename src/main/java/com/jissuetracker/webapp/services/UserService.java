package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.User;

import java.util.Map;

/**
 * Created by jovin on 14/2/16.
 */
public interface UserService {

    void add(User user)throws Exception;
    User getUserByUserName(String userName)throws Exception;
    User getUserByName(String name)throws Exception;
    Map<String,String> userDropdownList()throws Exception;

}
