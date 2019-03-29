package com.bits;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import com.bits.employee.Employee;
import com.bits.employee.EmployeeRepository;
import com.bits.employee.EmployeeService;
import com.bits.employee.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {
	
	@Autowired
    private EmployeeService employeeService;
 
    @MockBean
    private EmployeeRepository employeeRepository;

	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
		@Bean
		public EmployeeService employeeService(JpaRepository<Employee, Long> repository) {
            return new EmployeeServiceImpl(repository);
        }
 
	}
	
	@Before
	public void setUp() throws Exception {
		Employee shofiq = new Employee("Shofiq","Male",new SimpleDateFormat("yyyy-MM-dd").parse("1985-01-15"));	 
	    Mockito.when(employeeRepository.findByName(shofiq.getName()))
	      .thenReturn(shofiq);
	}
    
    
    @Test
    public void whenValidName_thenEmployeeShouldBeFound() throws Exception {
    	String name = "Shofiq";
        Employee found = employeeService.findByName(name);     
        assertThat(found.getName())
          .isEqualTo(name);
     }

}
