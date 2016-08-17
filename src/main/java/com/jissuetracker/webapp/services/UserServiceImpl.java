package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.UserDao;
import com.jissuetracker.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by jovin on 14/2/16.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public void add(User user) throws Exception {
        userDao.add(user);

    }

    public User getUserByEmail(String email) throws Exception {
        return userDao.getUserByEmail(email);
    }

    public User getUserByName(String name) throws Exception {
        return userDao.getUserByName(name);
    }

    public Map<String, String> userDropdownList() throws Exception {
        return userDao.projectUserDropdownList();
    }

    public User getUserById(Integer userId) throws Exception {
        return userDao.getUserById(userId);
    }

    public void update(User user) throws Exception {
        userDao.update(user);
    }

    public List<User> userList() throws Exception {
        return userDao.userList();
    }

    public void addOrUpdate(User user) throws Exception {
        userDao.addOrUpdate(user);
    }
}
