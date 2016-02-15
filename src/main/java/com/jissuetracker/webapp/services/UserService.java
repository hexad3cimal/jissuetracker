package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.User;

/**
 * Created by jovin on 14/2/16.
 */
public interface UserService {

    public void add(User user)throws Exception;
    public User getUserByUserName(String userName)throws Exception;
}
