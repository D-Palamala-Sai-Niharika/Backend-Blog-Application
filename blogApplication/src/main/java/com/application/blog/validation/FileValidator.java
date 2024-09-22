package com.application.blog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

	private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; //2MB
	private static final String[] VALID_MIME_TYPES= {"image/jpeg", "image/jpg", "image/png"};
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		
		//check file is null or empty
		if(file.isEmpty() || file==null) {	
			context.disableDefaultConstraintViolation(); // disable default constraint message
			context.buildConstraintViolationWithTemplate("File cannot be null or empty").addConstraintViolation();
			return false;
		}
		
		//check file size
		if(file.getSize()>MAX_FILE_SIZE) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File size exceeds maximum limit !!");
			return false;
		}
		
		
		//check file type
		String contentType = file.getContentType();
	    if (!Arrays.asList(VALID_MIME_TYPES).contains(contentType)) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("File type not supported !!").addConstraintViolation();
	        return false;
	    }
		
		return true;
	}

}
