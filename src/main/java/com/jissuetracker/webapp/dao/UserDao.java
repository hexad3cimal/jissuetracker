package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;

/**
 * Created by jovin on 10/2/16.
 */
public interface UserDao {

    public void add(User user)throws Exception;
    public User getUserByUserName(String userName)throws Exception;
}
