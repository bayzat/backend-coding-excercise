package com.bayzat.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
		public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "error");
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundExceptions(CompanyNotFoundException ex, WebRequest request)
			throws Exception {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "error");
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundExceptions(EmployeeNotFoundException ex, WebRequest request)
			throws Exception {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "error");
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DependantNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundExceptions(DependantNotFoundException ex, WebRequest request)
			throws Exception {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), "error");
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exRespone = new ExceptionResponse(new Date(), "Validation Failed",
				ex.getBindingResult().getFieldError().toString());
		return new ResponseEntity(exRespone, HttpStatus.BAD_REQUEST);
	}
}
