package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Projects;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
