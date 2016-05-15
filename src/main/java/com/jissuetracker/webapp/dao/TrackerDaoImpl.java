package com.jissuetracker.webapp.dao;


import com.jissuetracker.webapp.models.Trackers;
import org.hibernate.SessionFactory;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 21/2/16.
 */
@Repository("TrackerDao")
public class TrackerDaoImpl implements TrackerDao {

    @Autowired
    SessionFactory sessionFactory;

    //gets hashmap containing statuses
    public HashMap<String, String> trackerDropDownMap() throws Exception {

        List<Object []> trackersList = sessionFactory.getCurrentSession()
                .createQuery("select id,name from Trackers")
                .list();
        HashMap<String,String> trackerMap = new HashMap<String, String>();
        for (Object [] trackers : trackersList){
            trackerMap.put(trackers[0].toString(),trackers[1].toString());
        }

        return trackerMap;
    }

    public Trackers getById(Integer trackerId) throws Exception {
        return (Trackers)sessionFactory.getCurrentSession().get(Trackers.class,trackerId);
    }
}
