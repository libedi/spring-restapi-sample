package com.libedi.demo.framework.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ControllerExceptionHandler 
 * @author Sangjun, Park
 *
 */
@ControllerAdvice(annotations = Controller.class)
@Order(2)
public class ControllerExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected void handleBadRequest(IllegalArgumentException ex) {
		logger.error("Controller Exception : {}", ex.getMessage(), ex);
	}
}
