package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Issues;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

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

    public Integer getId() throws Exception {
        List<Issues> issuesList=  sessionFactory.getCurrentSession().createCriteria(Issues.class).list();
        Issues issue = issuesList.get(issuesList.size()-1);
        return issue.getId()+1;
    }

    public Boolean checkIfIssueExist(String issueTitle) throws Exception {
        return sessionFactory.getCurrentSession()
                .createQuery("from Issues where title =:title")
                .setParameter("title",issueTitle).list().isEmpty();
    }

    public HashMap<String, String> getIssueByTitleMap(String title) throws Exception {
        Issues issues = (Issues)sessionFactory.getCurrentSession()
                .createQuery("From Issues where title =:title")
                .setParameter("title",title).uniqueResult();

        HashMap<String,String> issueTitleMap = new HashMap<String, String>();
        issueTitleMap.put(issues.getTitle(),issues.getUrl());
        return issueTitleMap;
    }
}
