package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by jovin on 10/2/16.
 */
public interface UserDao {

    public void add(User user)throws Exception;
    void update(User user)throws Exception;
    public User getUserByEmail(String userName)throws Exception;
    public User getUserByName(String name)throws Exception;
    public Map<String,String> projectUserDropdownList()throws Exception;
    public User getUserById(Integer userId)throws Exception;
    List<User> userList()throws Exception;
}
