package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.IssuesUpdates;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jovin on 8/4/16.
 */

@Repository("IssueUpdateDao")
public class IssueUpdateDaoImpl implements  IssueUpdateDao {

    @Autowired
    SessionFactory sessionFactory;

    public void addIssueUpdate(IssuesUpdates issuesUpdates) throws Exception {

        sessionFactory.getCurrentSession().save(issuesUpdates);
    }

    public List<IssuesUpdates> issuesUpdatesList(Integer issueId) throws Exception {
        return sessionFactory.getCurrentSession().createCriteria(IssuesUpdates.class,"issueUpdates")
                .setFetchMode("issues", FetchMode.JOIN)
                .setFetchMode("users",FetchMode.JOIN)
                .setFetchMode("attachmentses",FetchMode.JOIN)
                .createAlias("issueUpdates.issues","issue")
                .add(Restrictions.eq("issue.id",issueId)).list();
    }
}
