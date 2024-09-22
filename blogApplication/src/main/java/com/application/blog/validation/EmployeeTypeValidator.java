package com.application.blog.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeTypeValidator implements ConstraintValidator<ValidEmployeeType, String> {

	@Override
	public boolean isValid(String empType, ConstraintValidatorContext context) {
		List<String> empTypes=Arrays.asList("permanent", "vendor");
		return empTypes.contains(empType);
	}

}
