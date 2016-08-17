package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.PriorityDao;
import com.jissuetracker.webapp.models.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by jovin on 13/8/16.
 */

@Service("PriorityService")
public class PriorityServiceImpl implements PriorityService {

    @Autowired
    PriorityDao priorityDao;

    @Transactional
    public void add(Priority priority) throws Exception {
        priorityDao.add(priority);
    }

    @Transactional(readOnly = true)
    public HashMap<Integer, String> priorityMap() throws Exception {
        return priorityDao.priorityMap();
    }

    @Transactional(readOnly = true)
    public Priority getByPriorityId(Integer priorityId) throws Exception {
        return priorityDao.getByPriorityId(priorityId);
    }
}
