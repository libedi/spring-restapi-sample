package com.libedi.demo.framework.config;

import java.util.Map;

import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libedi.demo.common.model.ErrorResponse;
import com.libedi.demo.framework.model.RequestScopeModel;
import com.libedi.demo.framework.model.ThreadScopeModel;

/**
 * Web Mvc Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Configuration to errorAttributes
	 * @return
	 */
	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> getErrorAttributes(final WebRequest webRequest,
					final boolean includeStackTrace) {
				Integer status = (Integer) webRequest.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
				return objectMapper.convertValue(
						new ErrorResponse(status == null ? "None" : HttpStatus.valueOf(status).getReasonPhrase()),
						Map.class);
			}
		};
	}
	
	/**
	 * declare rototype-scope bean
	 * @return
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ThreadScopeModel threadScopeModel() {
		return new ThreadScopeModel();
	}
	
	/**
	 * Configure thread-scope bean
	 * @return
	 */
	@Bean
	public ThreadLocalTargetSource threadLocalTargetSource() {
		ThreadLocalTargetSource threadLocalTargetSource = new ThreadLocalTargetSource();
		threadLocalTargetSource.setTargetBeanName("threadScopeModel");
		return threadLocalTargetSource;
	}
	
	/**
	 * declare request-scope bean ==> @RequestScope
	 * @return
	 */
//	@Bean
//	@Scope(value =  WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//	public RequestScopeModel requestScopeModel() {
//		return new RequestScopeModel();
//	}
	
}
