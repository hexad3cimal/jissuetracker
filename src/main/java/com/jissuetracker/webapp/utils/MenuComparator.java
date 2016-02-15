package com.jissuetracker.webapp.utils;

import com.jissuetracker.webapp.models.Menu;

import java.util.Comparator;

/**
 * Created by jovin on 12/2/16.
 */
public class MenuComparator implements Comparator<Menu> {

    public int compare(Menu o1, Menu o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
