package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Projects;

import java.util.List;

/**
 * Created by jovin on 14/2/16.
 */
public interface ProjectService {

    public void add(Projects project)throws Exception;
    public Projects getByProjectNameAlongWithUsers(String projectName)throws Exception;
    public Projects getByProjectNameAlongWithIssuesAndUsers(String projectName)throws Exception;
    public Boolean doesUserHasProject(String email,String projectName)throws Exception;
    List<Projects> projectsList()throws Exception;
    public void update(Projects projects)throws Exception;
    Projects getById(Integer id)throws Exception;
    List<Projects> userProjectsList(Integer userId)throws Exception;




}
