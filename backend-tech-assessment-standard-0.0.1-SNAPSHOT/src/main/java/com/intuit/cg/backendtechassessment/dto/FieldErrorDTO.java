package com.intuit.cg.backendtechassessment.dto;

/**
 **	To show Validation Error for every field in the input json 
 */
public class FieldErrorDTO {
    
    private String fieldName;
    private String fieldError;
     
    public FieldErrorDTO(String fieldName, String fieldError) {
        this.fieldName = fieldName;
        this.fieldError = fieldError;
    }

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldError() {
		return fieldError;
	}

	public void setFieldError(String fieldError) {
		this.fieldError = fieldError;
	}   
}