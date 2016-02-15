package com.jissuetracker.webapp.models;
// Generated 9 Feb, 2016 6:00:21 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Status generated by hbm2java
 */
@Entity
@Table(name = "status", catalog = "IssueTracker")
public class Status implements java.io.Serializable {

	private Integer id;
	private String name;
	private Set<Issues> issueses = new HashSet<Issues>(0);

	public Status() {
	}

	public Status(String name, Set<Issues> issueses) {
		this.name = name;
		this.issueses = issueses;
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

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
	public Set<Issues> getIssueses() {
		return this.issueses;
	}

	public void setIssueses(Set<Issues> issueses) {
		this.issueses = issueses;
	}

}
