package com.libedi.demo.framework.model;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * NotNull annotation for validation
 * @author Sangjun, Park
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface NotNull {
	
	/**
	 * @return field name to validate
	 */
	@AliasFor("field")
	String[] value() default {};
	
	/**
	 * @return field name to validate
	 */
	@AliasFor("value")
	String[] field() default {};
}