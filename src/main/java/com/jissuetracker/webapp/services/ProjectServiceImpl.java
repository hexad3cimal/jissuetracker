package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.ProjectDao;
import com.jissuetracker.webapp.models.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
