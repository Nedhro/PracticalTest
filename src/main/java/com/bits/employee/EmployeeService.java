package com.bits.employee;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EmployeeService {
    
    Employee AddNew(EmployeeDto accountDto) throws Exception;
    
    Employee findByName(String name);
    
    Page<Employee> getEmployeeLikeName(PageRequest page,String name);
    
    Page<Employee> getEmployeeByNameAndAge(PageRequest page,String name,int age);
    
    Page<Employee> getEmployeeWithAge(PageRequest page,int age);
        
    Page<Employee> findAll(int page,int size);
    
    List<Employee> findAll();
    
    Employee findById(Long id);
    
    void delete(Employee employee);
    
    List<EmployeeGenderReportDto> getGenderWiseEmployee();
    
}
