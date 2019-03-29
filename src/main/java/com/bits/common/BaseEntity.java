package com.bits.common;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bits.auth.AuthUser;


@MappedSuperclass
public abstract class BaseEntity<ID> {

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

	@Column(name = "last_modified_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifiedDate;
	
	@Column(name = "modified_by", nullable = true)
	private Long modified_by;
	
	public abstract ID getId();

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@PrePersist
	public void prePersist() {
		this.createdDate = new Date();
		this.lastModifiedDate = new Date();
		if(SecurityContextHolder.getContext().getAuthentication() !=null){
			AuthUser user = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
			this.modified_by = user.getId();
		}
	}

	@PreUpdate
	public void preUpdate() {
		this.lastModifiedDate = new Date();
	}
}
