package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Projects;


/**
 * Created by jovin on 14/2/16.
 */
public interface ProjectDao {

    public void add(Projects projects)throws Exception;
    public Projects projectHomeList(String projectName)throws Exception;
}
