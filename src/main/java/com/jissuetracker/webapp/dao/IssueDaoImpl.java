package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
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

    public List<IssueDto> projectIssuesList(String projectName) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class,"issues")
                .createAlias("issues.userByCreatedById","creator")
                .createAlias("issues.userByAssignedToId","assigned")
                .createAlias("issues.status","stats")
                .createAlias("issues.trackers","trackers")
                .createAlias("issues.projects","projects")
                .add(Restrictions.eq("projects.name",projectName))
                .setProjection(Projections.projectionList()
                .add(Projections.property("issues.title").as("title"))
                .add(Projections.property("creator.name").as("createdBy"))
                .add(Projections.property("assigned.name").as("assignedTo"))
                .add(Projections.property("stats.name").as("status"))
                .add(Projections.property("trackers.name").as("tracker"))
                .add(Projections.property("issues.createdOn").as("createdOn"))
                .add(Projections.property("issues.updatedOn").as("updatedOn"))
                .add(Projections.property("issues.donePercentage").as("donePercentage"))
                .add(Projections.property("issues.estimatedHours").as("estimatedHours"))
                .add(Projections.property("issues.description").as("description"))
                .add(Projections.property("issues.url").as("url")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = new ArrayList<IssueDto>();
        issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;

    }

    public Issues getById(Integer id) throws Exception {
        return (Issues) sessionFactory.getCurrentSession().createCriteria(Issues.class,"issue").
                setFetchMode("userByCreatedById", FetchMode.JOIN)
                .setFetchMode("userByAssignedToId",FetchMode.JOIN)
                .setFetchMode("status",FetchMode.JOIN)
                .setFetchMode("trackers",FetchMode.JOIN)
                .setFetchMode("projects",FetchMode.JOIN)
                .setFetchMode("issuesUpdateses",FetchMode.JOIN)
                .add(Restrictions.eq("id",id)).uniqueResult();
    }
}
