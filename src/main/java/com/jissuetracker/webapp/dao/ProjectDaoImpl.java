package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.utils.GetCurrentUserDetails;
import com.jissuetracker.webapp.utils.NotEmpty;
import com.terracotta.entity.RootEntity;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by jovin on 14/2/16.
 */
@Repository("projectDao")
public class ProjectDaoImpl implements ProjectDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    GetCurrentUserDetails getCurrentUserDetails;

    public void add(Projects project) throws Exception {

        sessionFactory.getCurrentSession().save(project);

    }

    public void update(Projects project) throws Exception {
        sessionFactory.getCurrentSession().update(project);

    }

    public Projects projectHomeList(String projectName) throws Exception {
        return (Projects) sessionFactory.getCurrentSession()
                .createCriteria(Projects.class,"project").add(Restrictions.eq("name",projectName))
                .setFetchMode("users", FetchMode.JOIN).uniqueResult();
    }

    public Projects getByName(String projectName) throws Exception {
        return (Projects) sessionFactory.getCurrentSession()
                .createCriteria(Projects.class,"project").add(Restrictions.eq("name",projectName))
                .setFetchMode("issueses", FetchMode.JOIN).setFetchMode("users",FetchMode.JOIN).uniqueResult();
    }

    public Boolean doesUserHasProject(String email,String projectName) throws Exception {
        Projects project = (Projects) sessionFactory.getCurrentSession()
                .createCriteria(Projects.class,"project")
                .setFetchMode("users",FetchMode.JOIN).add(Restrictions.eq("name",projectName)).uniqueResult();

        if (NotEmpty.notEmpty(project)){
        Set<User> userSet = project.getUsers();
        if (NotEmpty.notEmpty(userSet)){
            for(User user:userSet){
                if (user.getEmail().equalsIgnoreCase(email))
                    return true;
            }

        }
        }
        return false;
    }

    //retrieves project list based on user
    public List<Projects> projectsList() throws Exception {

        if(NotEmpty.notEmpty(getCurrentUserDetails.getDetails())) {
            if (getCurrentUserDetails.getDetails().getRoles().getId() == 1)
                return sessionFactory.getCurrentSession().createCriteria(Projects.class)
                        .setFetchMode("users", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            else {
                List<Projects> projectsList = sessionFactory.getCurrentSession().createCriteria(Projects.class)
                        .setFetchMode("users", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

                List<Projects> projectsFinalList = new ArrayList<Projects>();
                for (Projects p : projectsList) {
                    if (NotEmpty.notEmpty(p.getUsers())) {
                        if (p.getUsers().contains(getCurrentUserDetails.getDetails()))
                            projectsFinalList.add(p);
                    }
                }
                return projectsFinalList;
            }
        }else
            return null;

    }

    public Projects getById(Integer id) throws Exception {
        return (Projects)sessionFactory.getCurrentSession().get(Projects.class,id);
    }


}
