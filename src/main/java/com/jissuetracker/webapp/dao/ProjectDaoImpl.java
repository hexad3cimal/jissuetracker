package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Projects;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 14/2/16.
 */
@Repository("projectDao")
public class ProjectDaoImpl implements ProjectDao {

    @Autowired
    SessionFactory sessionFactory;

    public void add(Projects project) throws Exception {

        sessionFactory.getCurrentSession().save(project);

    }

    public Projects projectHomeList(String projectName) throws Exception {

//        Projects projects = (Projects) sessionFactory.getCurrentSession()
//                .createQuery("From Projects where name =:projectName")
//                .setParameter("projectName",projectName).uniqueResult();


        return (Projects) sessionFactory.getCurrentSession()
                .createCriteria(Projects.class,"project").add(Restrictions.eq("name",projectName))
                .setFetchMode("users", FetchMode.JOIN).uniqueResult();
    }

    public Projects getByName(String projectName) throws Exception {
        return (Projects) sessionFactory.getCurrentSession()
                .createCriteria(Projects.class,"project").add(Restrictions.eq("name",projectName))
                .setFetchMode("issueses", FetchMode.JOIN).uniqueResult();
    }


}
