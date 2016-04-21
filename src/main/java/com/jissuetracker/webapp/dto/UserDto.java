package com.jissuetracker.webapp.dto;

import com.jissuetracker.webapp.models.Projects;
import com.jissuetracker.webapp.models.Roles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jovin on 21/4/16.
 */
public class UserDto {

    private Integer id;
    private String roles;
    private String name;
    private String email;
    private String password;
    private List<String> projectses = new ArrayList<String>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getProjectses() {
        return projectses;
    }

    public void setProjectses(List<String> projectses) {
        this.projectses = projectses;
    }
}
