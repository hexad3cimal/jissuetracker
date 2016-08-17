package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    public void addOrUpdate(User user) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(user);

    }

    public void update(User user) throws Exception {
        sessionFactory.getCurrentSession().update(user);
    }

    //gets the user by email
    public User getUserByEmail(String email) throws Exception {
        return (User)sessionFactory.getCurrentSession()
                .createQuery("From User where email = :email ")
                .setParameter("email",email).uniqueResult();
    }

    //gets the user by name
    public User getUserByName(String name) throws Exception {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class,"user")
                .setFetchMode("projectses", FetchMode.JOIN)
                .add(Restrictions.eq("name",name)).uniqueResult();
    }

    //gets the users other than Administrator from the database
    public Map<String, String> projectUserDropdownList() throws Exception {

        List<User> userList = sessionFactory.getCurrentSession()
                .createCriteria(User.class,"user").
                        createAlias("user.roles","roles")
                .setFetchMode("roles",FetchMode.JOIN)
                .add(Restrictions.ne("roles.rolename","Administrator")).list();

        Map<String,String> dropdownMap = new HashMap<String, String>();
        if(NotEmpty.notEmpty(userList)) {
            for (User u: userList) {
                dropdownMap.put(u.getEmail(), u.getName() +" | "+u.getRoles().getRolename());
            }
        }
        return dropdownMap;
    }

    public User getUserById(Integer userId) throws Exception {
        return (User)sessionFactory.getCurrentSession().createCriteria(User.class)
                .setFetchMode("roles",FetchMode.JOIN)
                .setFetchMode("projectses",FetchMode.JOIN).add(Restrictions.eq("id",userId))
                .uniqueResult();
    }


    //gets users list along with projects associated with them
    public List<User> userList() throws Exception {
        return sessionFactory.getCurrentSession().createCriteria(User.class)
                .setFetchMode("roles",FetchMode.JOIN)
                .setFetchMode("projectses",FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
}
