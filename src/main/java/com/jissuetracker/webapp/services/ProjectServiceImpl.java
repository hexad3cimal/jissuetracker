package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.ProjectDao;
import com.jissuetracker.webapp.models.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jovin on 14/2/16.
 */
@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDao projectDao;

    public void add(Projects project) throws Exception {
        projectDao.add(project);

    }

    public Projects projectHomeList(String projectName) throws Exception {
        return projectDao.projectHomeList(projectName);
    }

    public Projects getByName(String projectName) throws Exception {
        return projectDao.getByName(projectName);
    }

    public Boolean doesUserHasProject(String email, String projectName) throws Exception {
        return projectDao.doesUserHasProject(email,projectName);
    }

    public List<Projects> projectsList() throws Exception {
        return projectDao.projectsList();
    }

    public void update(Projects projects) throws Exception {
        projectDao.update(projects);
    }

    public Projects getById(Integer id) throws Exception {
        return projectDao.getById(id);
    }
}
