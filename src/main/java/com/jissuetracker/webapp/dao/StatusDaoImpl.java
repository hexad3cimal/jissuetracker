package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Status;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 22/2/16.
 */
@Repository("StatusDao")
public class StatusDaoImpl implements StatusDao {

    @Autowired
    SessionFactory sessionFactory;

    //gets hashmap containing statuses
    public HashMap<Integer, String> statusDropDownMap() throws Exception {

        List<Status> statusList = sessionFactory.getCurrentSession()
                .createCriteria(Status.class).list();
        HashMap<Integer, String> statusDropDownMap = new HashMap<Integer, String>();
        if(NotEmpty.notEmpty(statusList)){
            for (Status status : statusList){
                statusDropDownMap.put(status.getId(),status.getName());
            }
        }
        return statusDropDownMap;
    }

    public Status getById(Integer statusId) throws Exception {
        return (Status)sessionFactory.getCurrentSession().get(Status.class,statusId);
    }

    public void add(Status status) throws Exception {

        sessionFactory.getCurrentSession().save(status);

    }

    public void update(Status status) throws Exception {

        sessionFactory.getCurrentSession().update(status);
    }
}
