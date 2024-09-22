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
@Constraint(validatedBy=EmployeeTypeValidator.class)
public @interface ValidEmployeeType {
	
	String message() default "Not a valid Employee type";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
