package com.libedi.demo.framework.model;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

/**
 * Thread-scope bean
 * @author Sangjun, Park
 *
 */
@Data
public class ThreadScopeModel {
	private static final Logger logger = LoggerFactory.getLogger(RequestScopeModel.class);
	
	private String value;
	
	@PreDestroy
	public void destroy() {
		// prototype-scope bean이라 앱 종료시에만 종료.
		logger.info("ThreadScopeModel Destroy");
	}
}
