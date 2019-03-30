package com.bits.employee;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.common.Response;
import com.bits.validation.RestErrorHandler;
import com.bits.validation.ValidationErrorDTO;

@RestController
@EnableAutoConfiguration
public class EmployeeController {
		
	@Autowired
	private EmployeeService service;
	
	@PostMapping(value = "/employee/registration")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewEmployee(@ModelAttribute EmployeeDto employeeDto,BindingResult bindingResult,HttpServletRequest request) {
				
		if (bindingResult.hasErrors()) {
			ValidationErrorDTO error = RestErrorHandler.processFieldErrors(bindingResult.getFieldErrors());
		    Response<ValidationErrorDTO> response = new Response<ValidationErrorDTO>(error,"error","Error! invalid field data");
			return ResponseEntity.ok(response);
		}		
		
		try {

			Employee employee = service.AddNew(employeeDto);
			
		    if (employee == null) {
		    	return ResponseEntity.ok(new Response<Employee>(employee,"error","Error! can not create employee"));
		    }
		    
		} catch(DuplicateEmployeeException e){
			return ResponseEntity.ok(new Response<Employee>(null,"error",e.getMessage()));
	    } catch (Exception e) {
	    	return ResponseEntity.ok(new Response<Employee>(null,"error",e.getMessage()));
	    }	   
		
	    Response<Employee> response = new Response<Employee>(null,"success"," Successfully create employee");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/api/employee/search")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> searchEmployee(
			@RequestBody Map<String, String> searchModel,
			@RequestParam( "page" ) int page,
			@RequestParam( "size" ) int size, 
			HttpServletRequest request) {

		Page<Employee> employees  = null;
		
		if(!StringUtils.isEmpty(searchModel.get("name")) && !StringUtils.isEmpty(searchModel.get("age"))){
			employees = service.getEmployeeByNameAndAge(
									new PageRequest(page,size), 
									searchModel.get("name"), 
									Integer.valueOf(searchModel.get("age"))
								);
		} else if(!StringUtils.isEmpty(searchModel.get("name"))){
			employees = service.getEmployeeLikeName(
									new PageRequest(page,size), 
									searchModel.get("name")
								);
		} else if(!StringUtils.isEmpty(searchModel.get("age"))){
			employees = service.getEmployeeWithAge(
									new PageRequest(page,size), 
									Integer.valueOf(searchModel.get("age"))
								);
		} else {
			employees = service.findAll(page,size);
		}
		
		if (employees == null) {	    
		    Response<EmployeeDto> response = new Response<EmployeeDto>(null,"error","Error null employee");
			return ResponseEntity.ok(response);
	    }
		
		final Page<EmployeeDto> employeesDTO = employees.map(this::convertToEmployeeDTO);	    	    
	    Response<Page<EmployeeDto>> response = new Response<Page<EmployeeDto>>(employeesDTO,"success","Successfully");    
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/api/v1/employee")
	public ResponseEntity<?> getAllEmployee
	  (@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, HttpServletRequest request ) {		
		try {
			Page<Employee> employees = service.findAll(page,size);	    
		    if (employees == null) {	    
			    Response<EmployeeDto> response = new Response<EmployeeDto>(null,"error","Error null employe list");
				return ResponseEntity.ok(response);
		    }
		    
		    final Page<EmployeeDto> employeeDTOs = employees.map(this::convertToEmployeeDTO);	    
			return ResponseEntity.ok(new Response<Page<EmployeeDto>>(employeeDTOs,"success","Successfully show employee list"));
		} catch(Exception e){
			return ResponseEntity.ok(new Response<Page<EmployeeDto>>(null,"error",e.getMessage()));
		}
	}
	
	@GetMapping(value = "/api/v1/employee/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id,HttpServletRequest request ) {
		
		try {
			Employee employee = service.findById(id);
		    
		    if (employee == null) {	    
			    Response<EmployeeDto> response = new Response<EmployeeDto>(null,"error","Employee not found");
				return ResponseEntity.ok(response);
		    }
		    
		    EmployeeDto employeeDto= convertToEmployeeDTO(employee);
		    Response<EmployeeDto> response = new Response<EmployeeDto>(employeeDto,"success","Successfully");    
		    return ResponseEntity.ok(response);
		} catch(Exception e){
			return ResponseEntity.ok(new Response<Page<EmployeeDto>>(null,"error",e.getMessage()));
		}
	}
	
	@DeleteMapping(value = "/api/v1/employee/delete/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") Long id,HttpServletRequest request ) {
		
		try {
			Employee employee = service.findById(id);
		    
		    if (employee == null) {
			    Response<EmployeeDto> response = new Response<EmployeeDto>(null, "error", "Employee not found");
				return ResponseEntity.ok(response);
		    }
		    
		    service.delete(employee);
		    
		    Response<EmployeeDto> response = new Response<EmployeeDto>(null,"success","Successfully");    
			return ResponseEntity.ok(response);
		} catch(Exception e){
			return ResponseEntity.ok(new Response<Page<EmployeeDto>>(null,"error",e.getMessage()));
		}
	}
	
	@GetMapping(value = "/api/v1/employee/gender")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> genderWiseEmployee(HttpServletRequest request ) {

		List<EmployeeGenderReportDto> genderWiseEmployees = service.getGenderWiseEmployee();
	    
	    if (genderWiseEmployees == null) {
		    Response<?> response = new Response<>(null,"error","Error in");
			return ResponseEntity.ok(response);
	    }
	    
	    Response<List<EmployeeGenderReportDto>> response = new Response<List<EmployeeGenderReportDto>>(genderWiseEmployees,"success","Successfully");    
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/content/{employeeId}")
	public void getUserContent(@PathVariable("employeeId") Long employeeId, HttpServletResponse response) {
        
        if(employeeId == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        
        Employee employee = service.findById(employeeId);
        if(employee == null) response.setStatus(HttpStatus.NOT_FOUND.value());
             
        response.setContentType("image/gif");            
        try {
            if(employee.getPic() != null) {
                response.getOutputStream().write(employee.getPic(), 0, employee.getPic().length);
                response.flushBuffer();
                response.setStatus(HttpStatus.OK.value());
            }
        } catch(IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }       
	}
	
	private EmployeeDto convertToEmployeeDTO(final Employee employee) {
			EmployeeDto dto = new EmployeeDto();
			dto.setId(employee.getId());
			dto.setName(employee.getName());
			dto.setGender(employee.getGender());
			dto.setDob(new SimpleDateFormat("yyyy-MM-dd").format(employee.getDob()));
			dto.setNote(employee.getNote());
			if(employee.getPic() !=null) {
				dto.setProfileUrl("/content/"+employee.getId());
			}
			return dto;
	}
	
}
