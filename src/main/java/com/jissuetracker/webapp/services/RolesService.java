package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Roles;

import java.util.List;

/**
 * Created by jovin on 10/2/16.
 */
public interface RolesService {

    List<Roles> rolesList() throws Exception;
    void add(Roles role)throws Exception;
    void remove(Roles role)throws Exception;
    Roles getByName(String roleName)throws Exception;


}
