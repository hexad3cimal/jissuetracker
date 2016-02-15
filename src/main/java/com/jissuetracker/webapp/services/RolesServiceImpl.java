package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.RolesDao;
import com.jissuetracker.webapp.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jovin on 10/2/16.
 */
@Transactional
@Service("rolesService")
public class RolesServiceImpl implements RolesService {

    @Autowired
    RolesDao rolesDao;

    public List<Roles> rolesList() throws Exception {
        return rolesDao.rolesList();
    }

    public void add(Roles role) throws Exception {

        rolesDao.add(role);
    }

    public void remove(Roles role) throws Exception {

        rolesDao.remove(role);

    }

    public Roles getByName(String roleName) throws Exception {
        return rolesDao.getByName(roleName);
    }
}
