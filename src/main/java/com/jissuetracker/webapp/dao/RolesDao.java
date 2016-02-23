package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Roles;

import java.util.List;

/**
 * Created by jovin on 10/2/16.
 */
public interface RolesDao {

    List<Roles> rolesList() throws Exception;
    Roles getByName(String roleName)throws Exception;
    void add(Roles role)throws Exception;
    void remove(Roles role)throws Exception;
}
