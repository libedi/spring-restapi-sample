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

import com.libedi.demo.framework.model.NotNull;

@Component
@Aspect
public class ValidAspect {
	
	private final Logger logger = LoggerFactory.getLogger(ValidAspect.class);
	
	@Pointcut("@annotaion(com.libedi.demo.framework.model.NotNull)"
			+ " or @annotation(com.libedi.demo.framework.model.NotEmpty)"
			+ " or @annotation(com.libedi.demo.framework.model.Pattern)")
	public void validPoint() {}
	
	@SuppressWarnings("unchecked")
	@Before("validPoint()")
	public void validateParameter(JoinPoint joinPoint) {
		Arrays.stream(joinPoint.getArgs()).filter(obj -> obj instanceof Map).map(obj -> {
			return (Map<String, Object>) obj;
		}).forEach(map -> {
			
		});
		;
		final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		for(Annotation annotation : signature.getMethod().getAnnotations()) {
			if(ClassUtils.isAssignable(annotation.annotationType(), NotNull.class)) {
				
			}
		}
		
		
		
		
		
		
	}
}
