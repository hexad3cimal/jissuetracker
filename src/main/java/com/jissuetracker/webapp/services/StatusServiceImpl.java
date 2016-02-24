package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.StatusDao;
import com.jissuetracker.webapp.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by jovin on 22/2/16.
 */
@Service("StatusService")
@Transactional
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusDao statusDao;

    public HashMap<String, String> statusDropDownMap() throws Exception {
        return statusDao.statusDropDownMap();
    }

    public Status getById(String statusId) throws Exception {
        return statusDao.getById(statusId);
    }

    public void add(Status status) throws Exception {
        statusDao.add(status);
    }

    public void update(Status status) throws Exception {
        statusDao.update(status);
    }
}
