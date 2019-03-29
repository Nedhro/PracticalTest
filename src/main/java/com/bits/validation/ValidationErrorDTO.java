package com.bits.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO implements Serializable {
	
	private static final long serialVersionUID = -8445943548965154778L;
	
	private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
	 
    public ValidationErrorDTO() {
 
    }
 
    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
    
}
