package com.jissuetracker.webapp.models;
// Generated 9 Feb, 2016 6:00:21 PM by Hibernate Tools 4.0.0

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IssuesUpdates generated by hbm2java
 */
@Entity
@Table(name = "issuesUpdates", catalog = "IssueTracker")
public class IssuesUpdates implements java.io.Serializable {

	private Integer id;
	private Issues issues;
	private String updatedBy;
	private String updatedByUserEmail;
	private Attachments attachments;
	private String updates;
	private Date date;
	private Set<Attachments> attachmentses = new HashSet<Attachments>(0);

	public IssuesUpdates() {
	}

	public IssuesUpdates(Issues issues, String updatedBy, Attachments attachments, String updates, Date date,
			Set<Attachments> attachmentses) {
		this.issues = issues;
		this.updatedBy = updatedBy;
		this.attachments = attachments;
		this.updates = updates;
		this.date = date;
		this.attachmentses = attachmentses;
	}

	public IssuesUpdates(Integer id,Issues issues, String updatedBy, Attachments attachments, String updates, Date date,
						 Set<Attachments> attachmentses) {
		this.issues = issues;
		this.updatedBy = updatedBy;
		this.attachments = attachments;
		this.updates = updates;
		this.date = date;
		this.attachmentses = attachmentses;
		this.id =id;
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

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issueId")
	public Issues getIssues() {
		return this.issues;
	}

	public void setIssues(Issues issues) {
		this.issues = issues;
	}



	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "attachementId")
	public Attachments getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}

	@Column(name = "updates")
	public String getUpdates() {
		return this.updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	@JsonSerialize(using = CustomDateFormatter.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issuesUpdates")
	public Set<Attachments> getAttachmentses() {
		return this.attachmentses;
	}

	public void setAttachmentses(Set<Attachments> attachmentses) {
		this.attachmentses = attachmentses;
	}

	@Column(name = "updatedBy")
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "updatedByUserEmail")
	public String getUpdatedByUserEmail() {
		return updatedByUserEmail;
	}

	public void setUpdatedByUserEmail(String updatedByUserEmail) {
		this.updatedByUserEmail = updatedByUserEmail;
	}
}
