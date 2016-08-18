package com.jissuetracker.webapp.dao;


import com.jissuetracker.webapp.models.Trackers;
import org.hibernate.SessionFactory;
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
    public HashMap<Integer, String> trackerDropDownMap() throws Exception {

        List<Trackers> trackersList = sessionFactory.getCurrentSession()
                .createCriteria(Trackers.class)
                .list();
        HashMap<Integer,String> trackerMap = new HashMap<Integer, String>();
        for (Trackers trackers : trackersList){
            trackerMap.put(trackers.getId(),trackers.getName());
        }

        return trackerMap;
    }

    public Trackers getById(Integer trackerId) throws Exception {
        return (Trackers)sessionFactory.getCurrentSession().get(Trackers.class,trackerId);
    }
}
