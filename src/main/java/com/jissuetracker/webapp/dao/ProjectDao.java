package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Projects;

import java.util.HashMap;
import java.util.List;


/**
 * Created by jovin on 14/2/16.
 */
public interface ProjectDao {

    public void add(Projects projects)throws Exception;
    public void update(Projects projects)throws Exception;
    public Projects getByProjectNameAlongWithUsers(String projectName)throws Exception;
    public Projects getByProjectNameAlongWithIssuesAndUsers(String projectName)throws Exception;
    public Boolean doesUserHasProject(String email,String projectName)throws Exception;
    List<Projects> projectsList()throws Exception;
    List<Projects> userProjectsList(Integer userId)throws Exception;
    Projects getById(Integer id)throws Exception;


}
