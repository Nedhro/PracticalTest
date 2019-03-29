package com.bits.employee;


import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bits.dto.EmployeeGenderReportDto;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByName(String name);
	
	@Query("SELECT e from Employee e where e.name like %:name%")
	Page<Employee> findByNameIgnoreCase(Pageable page, @Param("name") String name);
	
	@Query("SELECT e from Employee e where e.dob <= :age")
	Page<Employee> findByDate(Pageable page, @Param("age") Date age);
	
	@Query("SELECT e from Employee e where e.name like %:name% and e.dob <= :age")
	Page<Employee> findByNameAndAgeIgnoreCase(Pageable page, @Param("name") String name, @Param("age") Date age);
	
	@Query("SELECT new com.bits.dto.EmployeeGenderReportDto(e.gender, COUNT(e))" +
	           " FROM Employee e" +
	           " GROUP BY e.gender")
	List<EmployeeGenderReportDto> findEmployeeGroupBygender();
     
}
