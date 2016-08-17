package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.dto.IssueDto;
import com.jissuetracker.webapp.models.Issues;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    //gets the latest issue id
    public Integer getId() throws Exception {
        List<Issues> issuesList = sessionFactory.getCurrentSession().createCriteria(Issues.class).list();
        if (NotEmpty.notEmpty(issuesList)) {
            Issues issue = issuesList.get(issuesList.size() - 1);
            if (NotEmpty.notEmpty(issue))
                return issue.getId() + 1;

        }
        return 1;
    }

    //checks whether an issue with title @Param > "issueTitle" exist
    public Boolean checkIfIssueExist(String issueTitle) throws Exception {
        return sessionFactory.getCurrentSession()
                .createQuery("from Issues where title =:title")
                .setParameter("title", issueTitle).list().isEmpty();
    }

    public Boolean checkIfIssueExist(Integer issueId) throws Exception {
        return sessionFactory.getCurrentSession()
                .createQuery("from Issues where id =:id")
                .setParameter("id", issueId).list().isEmpty();    }


    /*
    retrieves issue tile, creator,assigned to user,status, tracker, project name, created date,
      updated date, done percentage, description, url, estimation
     */
    public List<IssueDto> projectIssuesList(String projectName) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto>  issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;

    }

    public List<IssueDto> projectIssuesCreatedByLoggedInUserList(String projectName, User user) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("userByCreatedById", user))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;
    }

    public List<IssueDto> projectIssuesAssignedToLoggedInUserList(String projectName, User user) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("userByAssignedToId", user))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;
    }

    public List<IssueDto> projectIssuesListBasedOnStatus(String projectName, String status) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("stats.name", status))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;    }

    public List<IssueDto> projectIssuesListBasedOnStatusCreatedByLoggedInUserList(String projectName, User user, String status) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("userByCreatedById", user))
                .add(Restrictions.eq("stats.name", status))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;    }

    public List<IssueDto> projectIssuesListBasedOnStatusAssignedToLoggedInUserList(String projectName, User user, String status) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("userByAssignedToId", user))
                .add(Restrictions.eq("stats.name", status))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;    }

    public List<IssueDto> projectIssuesListBasedOnTracker(String projectName,String status,  String tracker) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("trackers.name", tracker))
                .add(Restrictions.eq("stats.name", status))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;       }

    public List<IssueDto> projectIssuesListBasedOnTrackerCreatedByLoggedInUserList(String projectName, User user, String status, String tracker) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("userByCreatedById", user))
                .add(Restrictions.eq("stats.name", status))
                .add(Restrictions.eq("trackers.name", tracker))
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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;       }

    public List<IssueDto> projectIssuesListBasedOnTrackerAssignedToLoggedInUserList(String projectName, User user, String status, String tracker) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Issues.class, "issues")
                .createAlias("issues.userByCreatedById", "creator")
                .createAlias("issues.userByAssignedToId", "assigned")
                .createAlias("issues.status", "stats")
                .createAlias("issues.trackers", "trackers")
                .createAlias("issues.projects", "projects")
                .add(Restrictions.eq("projects.name", projectName))
                .add(Restrictions.eq("userByAssignedToId", user))
                .add(Restrictions.eq("stats.name", status))
                .add(Restrictions.eq("trackers.name", tracker))

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
                        .add(Projections.property("issues.url").as("url"))
                        .add(Projections.property("issues.id").as("id")));
        criteria.setResultTransformer(
                Transformers.aliasToBean(IssueDto.class));

        List<IssueDto> issueDtos = criteria.list();


        return issueDtos.isEmpty() ? null : issueDtos;       }

    //get issue by id
    public Issues getByIdWithUpdatesStatusTrackerPriorityAttachments(Integer id) throws Exception {
        return (Issues) sessionFactory.getCurrentSession().createCriteria(Issues.class, "issue").
                setFetchMode("userByCreatedById", FetchMode.JOIN)
                .setFetchMode("userByAssignedToId", FetchMode.JOIN)
                .setFetchMode("status", FetchMode.JOIN)
                .setFetchMode("trackers", FetchMode.JOIN)
                .setFetchMode("projects", FetchMode.JOIN)
                .setFetchMode("projects", FetchMode.JOIN)
                .setFetchMode("issuesUpdateses", FetchMode.JOIN)
                .setFetchMode("priority", FetchMode.JOIN)
                .setFetchMode("attachmentses", FetchMode.JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
    }


    //get issues assigned to the user by user id
    public List<Issues> getUserIssuesByUserId(Integer userId) throws Exception {
        return sessionFactory.getCurrentSession().createCriteria(Issues.class, "issues")
                .createAlias("issues.userByAssignedToId", "assigned")
                .add(Restrictions.eq("assigned.id", userId)).list();
    }


}
