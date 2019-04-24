package com.intuit.cg.backendtechassessment.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.intuit.cg.backendtechassessment.dto.ErrorDTO;
import com.intuit.cg.backendtechassessment.dto.FieldErrorDTO;

/**
 * * This class handles controller exceptions for invalid arguments
 */
@ControllerAdvice
public class InvalidInputExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		final ErrorDTO errorInfo = new ErrorDTO("Validation Error");

		final BindingResult result = ex.getBindingResult();
		final List<FieldError> fieldErrors = result.getFieldErrors();

		errorInfo.getFieldErrors().addAll(populateFieldErrors(fieldErrors));
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {	

		return new ResponseEntity<>( new ErrorDTO(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}
		
	/**
	 * Method populates a List of <FieldErrorDTO> objects. Each list
	 * item contains error message and name of a form field which caused the exception.  
	 */
	public List<FieldErrorDTO> populateFieldErrors(final List<FieldError> fieldErrorList) {
		
		final List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();
		for (final FieldError fieldError : fieldErrorList) {
			fieldErrors.add(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
			
		}
		return fieldErrors;
	}
	
}
