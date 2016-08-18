package com.jissuetracker.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jissuetracker.webapp.utils.IdGenerator;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jovin on 16/8/16.
 */

@MappedSuperclass
public abstract class SuperModelClass implements java.io.Serializable{


    private String uuId = IdGenerator.createId();


    private Integer version;


    private Date createdTimeStamp;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_time_stamp",nullable = false, length = 19)
    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    @JsonIgnore
    @Column(name = "uuid", updatable = false, nullable = false)
    public String getUuId() {
        return uuId;
    }
    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    @JsonIgnore
    @Version
    @Column(name = "version", nullable = false)
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||
                !(o instanceof SuperModelClass)) {

            return false;
        }

        SuperModelClass other
                = (SuperModelClass)o;

        // if the id is missing, return false
        if (uuId == null) return false;

        // equivalence by uuid
        return uuId.equals(other.getUuId());
    }

    public int hashCode() {
        if (uuId != null) {
            return uuId.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public String toString() {
        return this.getClass().getName()
                + "[id=" + uuId + "]";
    }
}
