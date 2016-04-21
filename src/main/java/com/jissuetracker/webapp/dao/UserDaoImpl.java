package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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

    public void update(User user) throws Exception {
        sessionFactory.getCurrentSession().update(user);
    }

    public User getUserByUserName(String userName) throws Exception {

       return (User)sessionFactory.getCurrentSession()
                .createQuery("From User where email = :email ")
                .setParameter("email",userName).uniqueResult();
    }

    public User getUserByName(String name) throws Exception {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class,"user")
                .setFetchMode("projectses", FetchMode.JOIN)
                .add(Restrictions.eq("name",name)).uniqueResult();
    }

    public Map<String, String> projectUserDropdownList() throws Exception {

       Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(User.class,"user").
                createAlias("user.roles","roles").add(Restrictions.ne("roles.rolename","Manager"))
                .add(Restrictions.ne("roles.rolename","Administrator"));
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("user.email"));
        projectionList.add(Projections.property("user.name"));

        List<Object[]> dropdownList = (List<Object[]>) criteria.setProjection(projectionList).list();

        Map<String,String> dropdownMap = new HashMap<String, String>();
        if(NotEmpty.notEmpty(dropdownList)) {
            for (Object[] O : dropdownList) {
                dropdownMap.put(O[0].toString(), O[1].toString());
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

    public List<User> userList() throws Exception {
        return sessionFactory.getCurrentSession().createCriteria(User.class)
                .setFetchMode("roles",FetchMode.JOIN)
                .setFetchMode("projectses",FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
}
