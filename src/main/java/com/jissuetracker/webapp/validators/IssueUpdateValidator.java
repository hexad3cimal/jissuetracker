package com.jissuetracker.webapp.validators;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jovin on 18/8/16.
 */
public class IssueUpdateValidator {

    private List<String> files = new ArrayList<String>();
    @NotEmpty
    private String updateText;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getUpdateText() {
        return updateText;
    }

    public void setUpdateText(String updateText) {
        this.updateText = updateText;
    }
}
