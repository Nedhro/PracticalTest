package com.bits.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.bits.employee.Employee;

public class EmployeeDto {
    
	private Long id;
	
	@NotNull
    @NotEmpty
    private String name;
     
    @NotNull
    @NotEmpty
    private String dob;
    
    @NotNull
    @NotEmpty
    private String gender;
    
    private MultipartFile file;
    
    private String note;
    
    private boolean hasFile;

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public static List<EmployeeDto> convertToDTO(List<Employee> employees){
		List<EmployeeDto> items =new ArrayList<>();
		for(Employee employee: employees){
			EmployeeDto dto = new EmployeeDto();
			dto.setId(employee.getId());
			dto.setName(employee.getName());
			dto.setGender(employee.getGender());
			dto.setDob(new SimpleDateFormat("yyyy-MM-dd").format(employee.getDob()));
			dto.setNote(employee.getNote());
			if(employee.getPic() !=null) dto.setHasFile(true);
			items.add(dto);
		}
		return items;
	}
    
}