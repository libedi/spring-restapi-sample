package com.libedi.demo.framework.model;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;

/**
 * Request-scope bean
 * @author Sangjun, Park
 *
 */
@RequestScope
@Component
@Data
public class RequestScopeModel {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestScopeModel.class);
	
	private String value;
	
	@PreDestroy
	public void destroy() {
		// request-scope bean이라 request 종료시 종료.
		logger.info("RequestScopeModel Destroy");
	}
}
