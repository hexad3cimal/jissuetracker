package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Menu;

/**
 * Created by jovin on 11/2/16.
 */
public interface MenuDao {

    Menu loadMenuByRole(String roleName)throws Exception;
}
