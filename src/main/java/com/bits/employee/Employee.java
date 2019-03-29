package com.bits.employee;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.bits.common.BaseEntity;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends BaseEntity<Long> implements Serializable {
	
	private static final long serialVersionUID = 6574747762954938484L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", length = 255)
    @NotNull
    @Size(max = 255)
    private String name;


    @Column(name = "DOB",nullable=true)
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    @Column(name = "GENDER", length = 50)
    @NotNull
    @Size(max = 50)
    private String gender;
    
    @Lob
    @Column(name="pic")
    private byte[] pic;
    
    @Lob
    @Column(name="NOTE")
    private String note;
    
    public Employee(){
    	
    }
    
    public Employee(String name,String gender,Date dob){
    	this.name = name;
    	this.gender = gender;
    	this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ",name=" + name + ", dob=" + dob
				+ "]";
	}
}