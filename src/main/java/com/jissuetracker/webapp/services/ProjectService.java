package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Projects;

import java.util.List;

/**
 * Created by jovin on 14/2/16.
 */
public interface ProjectService {

    public void add(Projects project)throws Exception;
    public Projects projectHomeList(String projectName)throws Exception;
    public Projects getByName(String projectName)throws Exception;
    public Boolean doesUserHasProject(String email,String projectName)throws Exception;
    List<Projects> projectsList()throws Exception;


}
