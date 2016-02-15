package com.jissuetracker.webapp.utils;

import com.jissuetracker.webapp.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jovin on 15/2/16.
 */
@Transactional
public class RoleUtil {

    @Autowired
    SessionFactory sessionFactory;

    public Boolean isManager(String username){

        return sessionFactory.getCurrentSession().createCriteria(User.class,"user").
                createAlias("user.roles","roles")
                .add(Restrictions.eq("user.email",username))
                .add(Restrictions.eq("roles.rolename","Manager")).list().isEmpty();

    }
}
