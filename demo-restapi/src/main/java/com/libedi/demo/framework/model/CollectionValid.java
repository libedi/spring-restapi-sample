package com.libedi.demo.framework.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;

import org.springframework.core.annotation.AliasFor;

/**
 * CollectionValid annotation
 * @author Sangjun, Park
 *
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE })
public @interface CollectionValid {
	
	@AliasFor("groups")
	Class<?>[] value() default { };
	
	@AliasFor("value")
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
