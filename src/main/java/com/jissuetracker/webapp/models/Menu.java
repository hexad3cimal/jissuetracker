package com.jissuetracker.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by jovin on 11/2/16.
 */
@Entity
@Table(name = "menu")
public class Menu extends SuperModelClass  implements Serializable{

    private int id;
    private String name;
    private String link;
    private String icon;
    private Roles roles;
    private Set<SubMenu> subMenuSet = new HashSet<SubMenu>();

    public Menu(){

    }

    public Menu(String name, String link, String icon, Roles roles, Set<SubMenu> subMenuSet){

        this.name = name;
        this.link = link;
        this.icon = icon;
        this.roles = roles;
        this.subMenuSet = subMenuSet;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "menu")
    public Set<SubMenu> getSubMenuSet() {
        return subMenuSet;
    }

    public void setSubMenuSet(Set<SubMenu> subMenuSet) {
        this.subMenuSet = subMenuSet;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
