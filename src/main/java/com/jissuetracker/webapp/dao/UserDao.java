package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by jovin on 10/2/16.
 */
public interface UserDao {

    void add(User user)throws Exception;
    void addOrUpdate(User user)throws Exception;
    void update(User user)throws Exception;
    User getUserByEmail(String userName)throws Exception;
    User getUserByName(String name)throws Exception;
    Map<String,String> projectUserDropdownList()throws Exception;
    User getUserById(Integer userId)throws Exception;
    List<User> userList()throws Exception;
}
