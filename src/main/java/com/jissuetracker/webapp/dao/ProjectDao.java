package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Projects;

import java.util.HashMap;
import java.util.List;


/**
 * Created by jovin on 14/2/16.
 */
public interface ProjectDao {

    public void add(Projects projects)throws Exception;
    public Projects projectHomeList(String projectName)throws Exception;
    public Projects getByName(String projectName)throws Exception;
    public Boolean doesUserHasProject(String email,String projectName)throws Exception;


}
