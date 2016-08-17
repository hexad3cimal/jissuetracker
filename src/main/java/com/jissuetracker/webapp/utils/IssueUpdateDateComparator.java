package com.jissuetracker.webapp.utils;

import com.jissuetracker.webapp.models.IssuesUpdates;

import java.util.Comparator;

/**
 * Created by jovin on 14/4/16.
 */

//For sorting issue updates according to update date
public class IssueUpdateDateComparator implements Comparator<IssuesUpdates> {

    public int compare(IssuesUpdates o1, IssuesUpdates o2) {
        if (o1.getCreatedTimeStamp() != null && o2.getCreatedTimeStamp() != null) {

            return o1.getCreatedTimeStamp().compareTo(o2.getCreatedTimeStamp());

        } else
            return 0;
    }
}
