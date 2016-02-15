package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Roles;

import java.util.List;

/**
 * Created by jovin on 10/2/16.
 */
public interface RolesDao {

    public List<Roles> rolesList() throws Exception;
    public Roles getByName(String roleName)throws Exception;
    public void add(Roles role)throws Exception;
    public void remove(Roles role)throws Exception;
}
