package com.jissuetracker.webapp.dto;

import com.jissuetracker.webapp.models.Roles;
import com.jissuetracker.webapp.models.SubMenu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jovin on 18/4/16.
 */
public class MenuDto {

    private int id;
    private String name;
    private String link;
    private String icon;
    private Roles roles;
    private Set<SubMenu> subMenuSet = new HashSet<SubMenu>();
    private List<String> subMenuList = new ArrayList<String>();

}
