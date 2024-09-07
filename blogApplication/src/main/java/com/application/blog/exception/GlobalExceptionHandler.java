package com.application.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		String msg=ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(msg,false),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> map=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach((error)->{
			String fieldName=((FieldError) error).getField();
			String errorMsg=error.getDefaultMessage();
			map.put(fieldName, errorMsg);
		});
		return new ResponseEntity<Map<String, String>>(map,HttpStatus.BAD_REQUEST);
	}
}

