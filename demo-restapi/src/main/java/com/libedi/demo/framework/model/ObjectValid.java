package com.libedi.demo.framework.model;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.libedi.demo.framework.handler.ObjectValidator;

/**
 * ObjectValid annotation
 * @author Sangjun, Park
 *
 */
@Retention(RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = { ObjectValidator.class })
public @interface ObjectValid {
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };

}
