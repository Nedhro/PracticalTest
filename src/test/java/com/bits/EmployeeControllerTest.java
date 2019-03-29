package com.bits;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
    private MockMvc mvc;
    
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mvc.perform(get("/api/v1/employee?page=0&size=2"))
        		.andDo(print())
        		.andExpect(status()
        		.isOk())
        		.andExpect(jsonPath("$.status", is("success")));
        	//	.andExpect(jsonPath("$.data.numberOfElements", is(2)));
    }
    
   /* @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
      throws Exception {
         
        Employee shofiq = new Employee("Shofiq","Male",new SimpleDateFormat("yyyy-MM-dd").parse("1985-01-15"));
     
        List<Employee> allEmployees = Arrays.asList(shofiq);
     
        given(service.findAll()).willReturn(allEmployees);


        mvc.perform(get("/api/v1/employee?page=0&size=2")
        		.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.status", is("success")));
		  //      .andExpect(jsonPath("$", hasSize(1)));
       // .andExpect(jsonPath("$[0].name", is(alex.getName()))).
       // andExpect(jsonPath("$[1].name", is(john.getName())))
       // andExpect(jsonPath("$[2].name", is(bob.getName())));
    }*/
    
}
