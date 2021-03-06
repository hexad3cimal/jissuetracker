package com.jissuetracker.webapp.models;
// Generated 16 Aug, 2016 4:24:53 PM by Hibernate Tools 4.0.0

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email") )
public class User extends SuperModelClass implements java.io.Serializable {

	private Integer id;
	private Roles roles;
	private String name;
	private String email;
	private String password;
	private String profilePic;
	private Set<Projects> projectses = new HashSet<Projects>(0);
	private Set<Issues> issuesesForAssignedToId = new HashSet<Issues>(0);
	private Set<Issues> issuesesForCreatedById = new HashSet<Issues>(0);
	private Set<IssuesUpdates> issuesUpdateses = new HashSet<IssuesUpdates>(0);

	public User() {
	}

	public User(Roles roles, String name, String email, String password) {
		this.roles = roles;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(Roles roles, String name, String email, String password,  String profilePic,
				Set<Projects> projectses, Set<Issues> issuesesForAssignedToId, Set<Issues> issuesesForCreatedById,
				Set<IssuesUpdates> issuesUpdateses) {
		this.roles = roles;
		this.name = name;
		this.email = email;
		this.password = password;
		this.profilePic = profilePic;
		this.projectses = projectses;
		this.issuesesForAssignedToId = issuesesForAssignedToId;
		this.issuesesForCreatedById = issuesesForCreatedById;
		this.issuesUpdateses = issuesUpdateses;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId", nullable = false)
	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", unique = true, nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	@Column(name = "password", nullable = false, length = 200)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(name = "profile_pic")
	public String getProfilePic() {
		return this.profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@ManyToMany(fetch=FetchType.LAZY ,mappedBy = "users")
//	@JoinTable(name="projectUserAssociation", catalog="IssueTracker", joinColumns = {
//			@JoinColumn(name="userId", nullable=false, updatable=false) }, inverseJoinColumns = {
//			@JoinColumn(name="projectId", nullable=false, updatable=false) })
	public Set<Projects> getProjectses() {
		return this.projectses;
	}

	public void setProjectses(Set<Projects> projectses) {
		this.projectses = projectses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userByAssignedToId")
	public Set<Issues> getIssuesesForAssignedToId() {
		return this.issuesesForAssignedToId;
	}

	public void setIssuesesForAssignedToId(Set<Issues> issuesesForAssignedToId) {
		this.issuesesForAssignedToId = issuesesForAssignedToId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userByCreatedById")
	public Set<Issues> getIssuesesForCreatedById() {
		return this.issuesesForCreatedById;
	}

	public void setIssuesesForCreatedById(Set<Issues> issuesesForCreatedById) {
		this.issuesesForCreatedById = issuesesForCreatedById;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<IssuesUpdates> getIssuesUpdateses() {
		return this.issuesUpdateses;
	}

	public void setIssuesUpdateses(Set<IssuesUpdates> issuesUpdateses) {
		this.issuesUpdateses = issuesUpdateses;
	}


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
