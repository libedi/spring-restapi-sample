package com.libedi.demo.framework.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
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
					for(String key : method.getAnnotation(NotNull.class).value()) {
						if (map.containsKey(key) == false || map.get(key) == null) {
							throw new IllegalArgumentException("Value is null. : " + key);
						}
					}
				}
				// not empty validation
				if(method.isAnnotationPresent(NotEmpty.class)) {
					for(String key : method.getAnnotation(NotEmpty.class).value()) {
						if (map.containsKey(key) == false || StringUtils.isEmpty(map.get(key))) {
							throw new IllegalArgumentException("Value is empty. : " + key);
						}
					}
				}
				// pattern match validation
				if(method.isAnnotationPresent(Pattern.class)) {
					final Pattern pattern = method.getAnnotation(Pattern.class);
					final String regexp = pattern.regExp();
					for(String key : pattern.value()) {
						if (map.containsKey(key) == false || (map.get(key) instanceof String) == false
								|| java.util.regex.Pattern.matches(regexp, String.valueOf(map.get(key))) == false) {
							throw new IllegalArgumentException("Value is not matched. : " + key);
						}
					}
				}
		});
	}
	
}
