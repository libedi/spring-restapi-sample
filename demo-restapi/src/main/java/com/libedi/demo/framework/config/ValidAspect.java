package com.libedi.demo.framework.config;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ehcache.impl.internal.classes.commonslang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.libedi.demo.framework.model.CollectionValid;
import com.libedi.demo.framework.model.NotEmpty;
import com.libedi.demo.framework.model.NotNull;
import com.libedi.demo.framework.model.Pattern;

/**
 * Validation Aspect
 * @author Sangjun, Park
 *
 */
@Aspect
@Component
public class ValidAspect {
	
	/**
	 * declaire validation pointcut 
	 */
	@Pointcut("@annotation(com.libedi.demo.framework.model.NotNull)"
			+ " or @annotation(com.libedi.demo.framework.model.NotEmpty)"
			+ " or @annotation(com.libedi.demo.framework.model.Pattern)")
	public void validPoint() {}
	
	/**
	 * Map validation
	 * @param joinPoint
	 */
	@SuppressWarnings("unchecked")
	@Before("validPoint()")
	public void validateParameter(final JoinPoint joinPoint) {
		final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// access paramMap
		Arrays.stream(joinPoint.getArgs()).filter(o -> o instanceof Map).map(o -> (Map<String, Object>) o)
			.findFirst().ifPresent(map -> {
				final Method method = signature.getMethod();
				// not null validation
				if(method.isAnnotationPresent(NotNull.class)) {
					for(final String key : method.getAnnotation(NotNull.class).value()) {
						if (map.containsKey(key) == false || map.get(key) == null) {
							throw new IllegalArgumentException("Value is null. : " + key);
						}
					}
				}
				// not empty validation
				if(method.isAnnotationPresent(NotEmpty.class)) {
					for(final String key : method.getAnnotation(NotEmpty.class).value()) {
						if (map.containsKey(key) == false || StringUtils.isEmpty(map.get(key))
								|| (map.get(key) instanceof Collection && CollectionUtils.isEmpty((Collection<?>) map.get(key)))) {
							throw new IllegalArgumentException("Value is empty. : " + key);
						}
					}
				}
				// pattern match validation
				if(method.isAnnotationPresent(Pattern.class)) {
					final Pattern pattern = method.getAnnotation(Pattern.class);
					final String regexp = pattern.regExp();
					for(final String key : pattern.value()) {
						if (map.containsKey(key) == false || (map.get(key) instanceof String) == false
								|| java.util.regex.Pattern.matches(regexp, String.valueOf(map.get(key))) == false) {
							throw new IllegalArgumentException("Value is not matched. : " + key);
						}
					}
				}
		});
	}
	
	@Autowired
	private Validator validator;
	
	@Pointcut("execution(* com.libedi.demo..*Controller.*(..))")
	public void listValidPoint() {}
	
	@Before("listValidPoint()")
	public void validateListParameter(final JoinPoint joinPoint) {
		final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		final Parameter[] parameters = method.getParameters();
		final Object[] args = joinPoint.getArgs();
		for (int i = 0, len = args.length; i < len; i++) {
			if (args[i] instanceof Collection && parameters[i].isAnnotationPresent(CollectionValid.class)) {
				final Collection<?> col = (Collection<?>) args[i];
				final CollectionValid valid = parameters[i].getAnnotation(CollectionValid.class);
				col.forEach(obj -> {
					final Set<ConstraintViolation<Object>> constraintViolations = this.validator.validate(obj,
							ArrayUtils.isEmpty(valid.value()) ? valid.groups() : valid.value());
					constraintViolations.forEach(cv -> {
						throw new IllegalArgumentException(cv.getPropertyPath().toString() + ":" + cv.getMessage());
					});
				});
				break;
			}
		}
	}
	
}
