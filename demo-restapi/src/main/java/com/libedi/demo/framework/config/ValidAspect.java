package com.libedi.demo.framework.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger logger = LoggerFactory.getLogger(ValidAspect.class);
	
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
	public void validateParameter(JoinPoint joinPoint) {
		final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// access paramMap
		Arrays.stream(joinPoint.getArgs()).filter(o -> o instanceof Map).map(o -> {
			return (Map<String, Object>) o;
		}).findFirst().ifPresent(map -> {
			for(Annotation annotation : signature.getMethod().getAnnotations()) {
				// not null validation
				if (ClassUtils.isAssignable(annotation.annotationType(), NotNull.class)) {
					logger.info("NotNull annotaion");
					final NotNull notNull = (NotNull) annotation;
					Arrays.stream(notNull.value()).forEach(key -> {
						if (map.containsKey(key) == false || map.get(key) == null) {
							throw new IllegalArgumentException("Value is null. : " + key);
						}
					});
				}
				// not empty validation 
				if (ClassUtils.isAssignable(annotation.annotationType(), NotEmpty.class)) {
					logger.info("NotEmpty annotaion");
					final NotEmpty notEmpty = (NotEmpty) annotation;
					Arrays.stream(notEmpty.value()).forEach(key -> {
						if (map.containsKey(key) == false || StringUtils.isEmpty(map.get(key))) {
							throw new IllegalArgumentException("Value is empty. : " + key);
						}
					});
				}
				// pattern match validation
				if (ClassUtils.isAssignable(annotation.annotationType(), Pattern.class)) {
					logger.info("Pattern annotaion");
					final Pattern pattern = (Pattern) annotation;
					Arrays.stream(pattern.value()).forEach(key -> {
						if (map.containsKey(key) == false || (map.get(key) instanceof String) == false
								|| java.util.regex.Pattern.matches(pattern.regExp(), String.valueOf(map.get(key))) == false) {
							throw new IllegalArgumentException("Value is not matched. : " + key);
						}
					});
				}
			}
		});
	}
	
}
