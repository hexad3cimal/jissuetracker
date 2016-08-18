package com.jissuetracker.webapp.validators;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jovin on 18/8/16.
 */
public class ProjectValidator {


    private Integer id;

    @NotEmpty
    private String description;

    @NotEmpty
    private String name;

    @NotEmpty
    List<String> users = new ArrayList<String>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
