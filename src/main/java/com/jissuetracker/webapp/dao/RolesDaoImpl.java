package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Roles;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jovin on 10/2/16.
 */
@Repository("rolesDao")
public class RolesDaoImpl implements RolesDao{

    @Autowired
    SessionFactory sessionFactory;


    public List<Roles> rolesList() throws Exception {
        return sessionFactory.getCurrentSession().createCriteria(Roles.class).list();
    }

    //get role by role name
    public Roles getByName(String roleName) throws Exception {
        return (Roles) sessionFactory.getCurrentSession()
                .createQuery("From Roles where rolename = :rolename ")
                .setParameter("rolename",roleName).uniqueResult();
    }

    public void add(Roles role) throws Exception {

        sessionFactory.getCurrentSession().save(role);
    }

    public void remove(Roles role) throws Exception {

        sessionFactory.getCurrentSession().delete(role);

    }
}
