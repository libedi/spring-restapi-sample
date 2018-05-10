package com.libedi.demo.framework.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

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
	 * parameter validation
	 * @param joinPoint
	 */
	@SuppressWarnings("unchecked")
	@Before("validPoint()")
	public void validateParameter(final JoinPoint joinPoint) {
		final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// access paramMap
		Arrays.stream(joinPoint.getArgs()).filter(o -> o instanceof Map).map(o -> (Map<String, Object>) o)
			.findFirst().ifPresent(map -> {
				for(Annotation annotation : signature.getMethod().getAnnotations()) {
					// not null validation
					if (ClassUtils.isAssignable(annotation.annotationType(), NotNull.class)) {
						final NotNull notNull = (NotNull) annotation;
						for(String key : notNull.value()) {
							if (map.containsKey(key) == false || map.get(key) == null) {
								throw new IllegalArgumentException("Value is null. : " + key);
							}
						}
					}
					// not empty validation 
					if (ClassUtils.isAssignable(annotation.annotationType(), NotEmpty.class)) {
						final NotEmpty notEmpty = (NotEmpty) annotation;
						for(String key : notEmpty.value()) {
							if (map.containsKey(key) == false || StringUtils.isEmpty(map.get(key))) {
								throw new IllegalArgumentException("Value is empty. : " + key);
							}
						}
					}
					// pattern match validation
					if (ClassUtils.isAssignable(annotation.annotationType(), Pattern.class)) {
						final Pattern pattern = (Pattern) annotation;
						final String regexp = pattern.regExp();
						for(String key : pattern.value()) {
							if (map.containsKey(key) == false || (map.get(key) instanceof String) == false
									|| java.util.regex.Pattern.matches(regexp, String.valueOf(map.get(key))) == false) {
								throw new IllegalArgumentException("Value is not matched. : " + key);
							}
						}
					}
				}
		});
	}
	
}
