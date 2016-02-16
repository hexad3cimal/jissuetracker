package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.UserDao;
import com.jissuetracker.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User getUserByUserName(String userName) throws Exception {
        return userDao.getUserByUserName(userName);
    }

    public Map<String, String> userDropdownList() throws Exception {
        return userDao.userDropdownList();
    }
}
