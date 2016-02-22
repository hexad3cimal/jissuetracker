package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.TrackerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by jovin on 21/2/16.
 */
@Transactional
@Service("TrackerService")
public class TrackerServiceImpl implements TrackerService {

    @Autowired
    TrackerDao trackerDao;

    public HashMap<String, String> trackerDropDownMap() throws Exception {
        return trackerDao.trackerDropDownMap();
    }
}
