package com.jissuetracker.webapp.utils;

import com.jissuetracker.webapp.models.Menu;
import com.jissuetracker.webapp.models.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jovin on 11/2/16.
 */
public class UserSession implements Serializable {

    private User user;
    private List<Menu> menuList;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }


}
