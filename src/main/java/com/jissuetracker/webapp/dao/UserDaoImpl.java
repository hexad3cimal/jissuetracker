package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, String> userDropdownList() throws Exception {
        List<Object[]> dropdownList = (List<Object[]>)sessionFactory.getCurrentSession()
                .createQuery("select email ,name from User").list();
        Map<String,String> dropdownMap = new HashMap<String, String>();
        for (Object[] O : dropdownList){
            dropdownMap.put(O[0].toString(),O[1].toString());
        }
        return dropdownMap;
    }
}
