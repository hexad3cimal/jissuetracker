package com.jissuetracker.webapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jissuetracker.webapp.utils.CustomDateFormatter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by jovin on 22/3/16.
 */
public class IssueDto {
    private Date createdOn;
    private Date updatedOn;
    private Integer donePercentage;
    private String estimatedHours;
    private Date endDate;
    private String description;
    private String title;
    private String url;
    private String createdBy;
    private String assignedTo;
    private String status;
    private String tracker;

    @JsonSerialize(using = CustomDateFormatter.class)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @JsonSerialize(using = CustomDateFormatter.class)
    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getDonePercentage() {
        return donePercentage;
    }

    public void setDonePercentage(Integer donePercentage) {
        this.donePercentage = donePercentage;
    }

    public String getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(String estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }
}
