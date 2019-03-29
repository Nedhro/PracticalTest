package com.bits.employee;

public class EmployeeGenderReportDto {
    	
    private String gender;    
    private Long count;
    
    public EmployeeGenderReportDto(String gender,Long count){
    	this.gender = gender;
    	this.count = count;
    }

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
    
}