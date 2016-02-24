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
public class StatusDaoImpl implements StatusDao{

    @Autowired
    SessionFactory sessionFactory;

    public HashMap<String, String> statusDropDownMap() throws Exception {

        List<Object []> statusList = sessionFactory.getCurrentSession()
                .createQuery("select id,name from Status").list();
        HashMap<String, String> statusDropDownMap = new HashMap<String, String>();
        if(NotEmpty.notEmpty(statusList)){
            for (Object[] status : statusList){
                statusDropDownMap.put(status[0].toString(),status[1].toString());
            }
        }
        return statusDropDownMap;
    }

    public Status getById(String statusId) throws Exception {
        return (Status)sessionFactory.getCurrentSession().get(Status.class,statusId);
    }

    public void add(Status status) throws Exception {

        sessionFactory.getCurrentSession().save(status);

    }

    public void update(Status status) throws Exception {

        sessionFactory.getCurrentSession().update(status);
    }
}
