package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Issues;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jovin on 24/2/16.
 * Dao for handling Issues
 */

@Repository("IssueDao")
public class IssueDaoImpl implements IssueDao {

    @Autowired
    SessionFactory sessionFactory;

    public void add(Issues issue) throws Exception {

        sessionFactory.getCurrentSession().save(issue);
    }

    public void update(Issues issue) throws Exception {

        sessionFactory.getCurrentSession().update(issue);
    }
}
