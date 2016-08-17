package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Attachments;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jovin on 10/8/16.
 */

@Repository("AttachmentDao")
public class AttachmentDaoImpl implements AttachmentDao {

    @Autowired
    SessionFactory sessionFactory;

    public void add(Attachments attachments) throws Exception {
        sessionFactory.getCurrentSession().save(attachments);
    }

    public void delete(Integer attachmentId) throws Exception {
        sessionFactory.getCurrentSession()
                .delete(sessionFactory.getCurrentSession().get(Attachments.class,attachmentId));

    }

    public Attachments getByIdWithIssuesAndIssueUpdates(Integer attachmentId) throws Exception {
        return (Attachments) sessionFactory.getCurrentSession().createCriteria(Attachments.class)
                .add(Restrictions.eq("id",attachmentId))
                .setFetchMode("issues", FetchMode.JOIN)
                .setFetchMode("issuesUpdates", FetchMode.JOIN).uniqueResult();
    }
}
