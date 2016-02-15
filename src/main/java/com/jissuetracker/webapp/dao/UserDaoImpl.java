package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jovin on 10/2/16.
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public void add(User user) throws Exception {
        sessionFactory.getCurrentSession().save(user);
    }

    public User getUserByUserName(String userName) throws Exception {

       return (User)sessionFactory.getCurrentSession()
                .createQuery("From User where email = :email ")
                .setParameter("email",userName).uniqueResult();
    }
}
