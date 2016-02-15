package com.jissuetracker.webapp.models;

import javax.persistence.*;

/**
 * Created by jovin on 11/2/16.
 */
@Entity
@Table(name = "subMenu")
public class SubMenu {

    private int id;
    private String name;
    private String link;
    private String icon;
    private Menu menu;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    public Menu getMenu() {

        return menu;
    }

    public void setMenu(Menu menu) {

        this.menu = menu;
    }
}


