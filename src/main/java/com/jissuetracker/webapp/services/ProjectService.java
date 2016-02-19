package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Projects;

/**
 * Created by jovin on 14/2/16.
 */
public interface ProjectService {

    public void add(Projects project)throws Exception;
    public Projects projectHomeList(String projectName)throws Exception;
    public Projects getByName(String projectName)throws Exception;


}
