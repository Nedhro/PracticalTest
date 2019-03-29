package com.bits.employee;


public class DuplicateEmployeeException extends Exception {
	
	private static final long serialVersionUID = -2614870763746506596L;
	private Employee employee;
    private String message;

    public DuplicateEmployeeException(Employee employee, String message) {
        this.employee = employee;
        this.message = message;
    }

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
}
