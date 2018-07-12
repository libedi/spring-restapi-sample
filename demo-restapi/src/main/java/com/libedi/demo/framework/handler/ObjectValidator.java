package com.libedi.demo.framework.handler;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.libedi.demo.framework.model.ObjectValid;

/**
 * ObjectValidator
 * @author Sangjun, Park
 *
 */
public class ObjectValidator implements ConstraintValidator<ObjectValid, Object> {

	@Autowired
	private Validator validator;
	
	private Class<?>[] groups;
	
	@Override
	public void initialize(ObjectValid constraintAnnotation) {
		this.groups = constraintAnnotation.groups();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(value != null) {
			final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(value, groups);
			if(constraintViolations.size() > 0) {
				constraintViolations.stream().findFirst().ifPresent(cv -> {
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(cv.getMessage())
						.addPropertyNode(cv.getPropertyPath().toString())
						.addConstraintViolation();
				});
				return false;
			}
		}
		return true;
	}

}
