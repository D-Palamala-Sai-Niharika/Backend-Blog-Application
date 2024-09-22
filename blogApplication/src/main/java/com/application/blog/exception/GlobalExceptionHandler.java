package com.application.blog.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.application.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		String msg=ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(msg,false),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> map=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach((error)->{
			String fieldName=((FieldError) error).getField();
			String errorMsg=error.getDefaultMessage();
			map.put(fieldName, errorMsg);
		});
		return new ResponseEntity<Map<String, String>>(map,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex){
		//String msg=ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse("File size exceeds the maximum limit!",false),HttpStatus.PAYLOAD_TOO_LARGE);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> handleIOException(IOException ex){
		String msg=ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse("Could not upload the file :" + msg, false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FileNotSupportedException.class)
	public ResponseEntity<ApiResponse> handleFileNotSupportedException(FileNotSupportedException ex){
		String msg=ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse( msg, false),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ApiResponse> handleFileNotFoundException(FileNotFoundException ex){
		String msg=ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse("File not Found !!", false),HttpStatus.NOT_FOUND);
	}
}

