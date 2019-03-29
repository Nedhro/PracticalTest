package com.bits.employee;

import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bits.common.CommonServiceImpl;
import com.bits.common.Utill;
import com.bits.dto.EmployeeDto;
import com.bits.dto.EmployeeGenderReportDto;

@Service
@Transactional
public class EmployeeServiceImpl extends CommonServiceImpl<Employee> implements EmployeeService {

	@Autowired
    private EmployeeRepository repository;
    
    public EmployeeServiceImpl(JpaRepository<Employee, Long> repository) {
		super(repository);		
		this.repository = (EmployeeRepository)repository;
	}
    
    @Transactional
    @Override
    public Employee AddNew(EmployeeDto accountDto) throws Exception {
    	
    	Employee employee = findByName(accountDto.getName());
        if(employee != null) {
            throw new DuplicateEmployeeException(employee, "Error! employee already exist with this name");
        }
        
    	if(accountDto.getId() != null){
    		employee = findById(accountDto.getId());
    	} else {
    		employee = new Employee();
    	}

        employee.setName(accountDto.getName());
        employee.setGender(accountDto.getGender());
        employee.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(accountDto.getDob()));
        if(accountDto.getFile() != null) employee.setPic(accountDto.getFile().getBytes());
        if(accountDto.getNote() != null)employee.setNote(accountDto.getNote());
        return repository.save(employee);
    }

	@Override
	public Page<Employee> findAll(int page, int size) {
		return repository.findAll(new PageRequest(page ,size));
	}
	
	@Override
	public List<Employee> findAll() {
		return repository.findAll();
	}
	

	@Override
	public Employee findById(Long id) {
		return repository.findOne(id);
	}


	@Override
	public List<EmployeeGenderReportDto> getGenderWiseEmployee() {
		return repository.findEmployeeGroupBygender();
	}

	@Override
	public Page<Employee> getEmployeeLikeName(PageRequest page, String name) {
		return repository.findByNameIgnoreCase(page,name);
	}

	@Override
	public Page<Employee> getEmployeeWithAge(PageRequest page, int age) {
		return repository.findByDate(page, Utill.getDateByAge(age));
	}

	@Override
	public Page<Employee> getEmployeeByNameAndAge(PageRequest page, String name, int age) {
		return repository.findByNameAndAgeIgnoreCase(page, name, Utill.getDateByAge(age));
	}

	@Override
	public Employee findByName(String name) {
		return repository.findByName(name);
	}

}