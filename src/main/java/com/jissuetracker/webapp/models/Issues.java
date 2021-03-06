package com.jissuetracker.webapp.models;
// Generated 16 Aug, 2016 4:07:13 PM by Hibernate Tools 4.0.0

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Issues generated by hbm2java
 */
@Entity
@Table(name = "issues", catalog = "IssueTracker")
public class Issues extends SuperModelClass implements java.io.Serializable {

	private Integer id;
	private Priority priority;
	private Projects projects;
	private User userByCreatedById;
	private User userByAssignedToId;
	private Status status;
	private Trackers trackers;
	private Date updatedOn;
	private Integer donePercentage;
	private String estimatedHours;
	private Date endDate;
	private String description;
	private String title;
	private String url;
	private String readByAssigned;
	private Set<IssuesUpdates> issuesUpdateses = new HashSet<IssuesUpdates>(0);
	private Set<Attachments> attachmentses = new HashSet<Attachments>(0);

	public Issues() {
	}



	public Issues(Priority priority, Projects projects, User userByCreatedById, User userByAssignedToId, Status status,
				  Trackers trackers, Date endDate, String description,
				  String title) {
		this.priority = priority;
		this.projects = projects;
		this.userByCreatedById = userByCreatedById;
		this.userByAssignedToId = userByAssignedToId;
		this.status = status;
		this.trackers = trackers;
		this.endDate = endDate;
		this.description = description;
		this.title = title;
	}

	public Issues(Priority priority, Projects projects, User userByCreatedById, User userByAssignedToId, Status status,
				  Trackers trackers,  Date updatedOn, Integer donePercentage,
				  String estimatedHours, Date endDate, String description, String title, String url, String readByAssigned,
				  Set<IssuesUpdates> issuesUpdateses, Set<Attachments> attachmentses) {
		this.priority = priority;
		this.projects = projects;
		this.userByCreatedById = userByCreatedById;
		this.userByAssignedToId = userByAssignedToId;
		this.status = status;
		this.trackers = trackers;
		this.updatedOn = updatedOn;
		this.donePercentage = donePercentage;
		this.estimatedHours = estimatedHours;
		this.endDate = endDate;
		this.description = description;
		this.title = title;
		this.url = url;
		this.readByAssigned = readByAssigned;
		this.issuesUpdateses = issuesUpdateses;
		this.attachmentses = attachmentses;
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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "priority_id", nullable = false)
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId", nullable = false)
	public Projects getProjects() {
		return this.projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdById", nullable = false)
	public User getUserByCreatedById() {
		return this.userByCreatedById;
	}

	public void setUserByCreatedById(User userByCreatedById) {
		this.userByCreatedById = userByCreatedById;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignedToId", nullable = false)
	public User getUserByAssignedToId() {
		return this.userByAssignedToId;
	}

	public void setUserByAssignedToId(User userByAssignedToId) {
		this.userByAssignedToId = userByAssignedToId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statusId", nullable = false)
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trackerId", nullable = false)
	public Trackers getTrackers() {
		return this.trackers;
	}

	public void setTrackers(Trackers trackers) {
		this.trackers = trackers;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedOn", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "donePercentage")
	public Integer getDonePercentage() {
		return this.donePercentage;
	}

	public void setDonePercentage(Integer donePercentage) {
		this.donePercentage = donePercentage;
	}

	@Column(name = "estimatedHours", length = 10)
	public String getEstimatedHours() {
		return this.estimatedHours;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endDate", nullable = false, length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "title", nullable = false, length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "url", length = 600)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "readByAssigned", length = 45)
	public String getReadByAssigned() {
		return this.readByAssigned;
	}

	public void setReadByAssigned(String readByAssigned) {
		this.readByAssigned = readByAssigned;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issues")
	public Set<IssuesUpdates> getIssuesUpdateses() {
		return this.issuesUpdateses;
	}

	public void setIssuesUpdateses(Set<IssuesUpdates> issuesUpdateses) {
		this.issuesUpdateses = issuesUpdateses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issues")
	public Set<Attachments> getAttachmentses() {
		return this.attachmentses;
	}

	public void setAttachmentses(Set<Attachments> attachmentses) {
		this.attachmentses = attachmentses;
	}

}
