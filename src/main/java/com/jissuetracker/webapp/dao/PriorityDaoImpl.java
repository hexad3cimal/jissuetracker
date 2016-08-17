package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Priority;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 13/8/16.
 */

@Repository("PriorityDao")
public class PriorityDaoImpl implements PriorityDao {

    @Autowired
    SessionFactory sessionFactory;

    public void add(Priority priority) throws Exception {
        sessionFactory.getCurrentSession().save(priority);
    }

    public HashMap<Integer, String> priorityMap() throws Exception {

        List<Priority> priorityList = sessionFactory.getCurrentSession()
                .createCriteria(Priority.class).list();

        HashMap<Integer,String> priorityMap = new HashMap<Integer, String>();
        if(NotEmpty.notEmpty(priorityList)){
            for (Priority p : priorityList)
                    priorityMap.put(p.getId(),p.getName());
        }
        return priorityMap;
    }

    public Priority getByPriorityId(Integer priorityId) throws Exception {
        return (Priority)sessionFactory.getCurrentSession().get(Priority.class,priorityId);
    }
}
