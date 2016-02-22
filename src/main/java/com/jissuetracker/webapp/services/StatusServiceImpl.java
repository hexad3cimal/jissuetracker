package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.StatusDao;
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
}
