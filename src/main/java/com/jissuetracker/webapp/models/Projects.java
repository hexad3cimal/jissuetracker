package com.jissuetracker.webapp.models;
// Generated 9 Feb, 2016 6:00:21 PM by Hibernate Tools 4.0.0

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jissuetracker.webapp.utils.CustomDateFormatter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Projects generated by hbm2java
 */
@Entity
@Table(name = "projects", catalog = "IssueTracker")
public class Projects implements java.io.Serializable {

	private Integer id;
	private String name;
	private String description;
	private String url;
	private Date createdOn;
	private Date updatedOn;
	private String manager;
	private Set<User> users = new HashSet<User>(0);
	private Set<Issues> issueses = new HashSet<Issues>(0);

	public Projects() {
	}

	public Projects(String name) {
		this.name = name;
	}

	public Projects(Integer id,String name, String description, String url, Date createdOn, Date updatedOn, Set<User> users,
					Set<Issues> issueses, String manager) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.users = users;
		this.issueses = issueses;
		this.manager = manager;
	}

	public Projects(String name, String description, String url, Date createdOn, Date updatedOn, Set<User> users,
			Set<Issues> issueses, String manager) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.users = users;
		this.issueses = issueses;
		this.manager = manager;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonSerialize(using = CustomDateFormatter.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdOn", length = 19,insertable = false, nullable = false)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@JsonSerialize(using = CustomDateFormatter.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedOn", length = 19,insertable = false, nullable = false)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "projectUserAssociation",  joinColumns = {
			@JoinColumn(name = "projectId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "userId", nullable = false, updatable = false) })
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projects")
	public Set<Issues> getIssueses() {
		return this.issueses;
	}

	public void setIssueses(Set<Issues> issueses) {
		this.issueses = issueses;
	}

    @Column(name = "manager")
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
