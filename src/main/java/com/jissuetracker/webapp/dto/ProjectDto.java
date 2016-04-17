package com.jissuetracker.webapp.dto;

import com.jissuetracker.webapp.models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jovin on 15/4/16.
 */

public class ProjectDto {

    private Integer id;
    private String name;
    private String description;
    private List<String> userList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
