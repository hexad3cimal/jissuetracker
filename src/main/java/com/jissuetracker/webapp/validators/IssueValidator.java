package com.jissuetracker.webapp.validators;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jovin on 18/8/16.
 */
public class IssueValidator {

    @NotEmpty
    private String title;
    private List<String> files = new ArrayList<String>();
    @NotNull
    private Integer priority;
    @NotNull
    private Integer tracker;
    @NotNull
    private Integer status;
    @NotNull
    private Integer assigned;
    @NotEmpty
    private String completionDate;
    @NotEmpty
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getTracker() {
        return tracker;
    }

    public void setTracker(Integer tracker) {
        this.tracker = tracker;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
