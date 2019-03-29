package com.bits;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.bits.employee.Employee;
import com.bits.employee.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EmployeeRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Test
    public void whenFindByName_thenReturnEmployee() throws Exception{
        // given
        Employee shofiq = new Employee("Shofiq","Male",new SimpleDateFormat("yyyy-MM-dd").parse("1985-01-15"));
        entityManager.persist(shofiq);
        entityManager.flush();
     
        // when
        Employee found = employeeRepository.findByName(shofiq.getName());
     
        // then
        assertThat(found.getName())
          .isEqualTo(shofiq.getName());
    }

}
