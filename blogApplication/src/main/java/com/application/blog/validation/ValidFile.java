package com.application.blog.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=FileValidator.class)
public @interface ValidFile {
	

	String message() default "Invalid File !!";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
