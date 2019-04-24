package com.intuit.cg.backendtechassessment.dto;

import java.util.ArrayList;
import java.util.List;
/**
 **	To show Validation Error details 
 */
public class ErrorDTO {
   
    private String message;    
    private List< FieldErrorDTO > fieldErrors = new ArrayList<FieldErrorDTO>();
     
    public ErrorDTO() {}
     
    public ErrorDTO(String message) {          
        this.message = message;
    }
     
    public ErrorDTO(List< FieldErrorDTO > fieldErrors, String message) {
            this.fieldErrors = fieldErrors;           
            this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}

