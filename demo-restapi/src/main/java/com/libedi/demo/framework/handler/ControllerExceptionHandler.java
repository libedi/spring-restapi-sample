package com.libedi.demo.framework.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.libedi.demo.common.model.ErrorResponse;

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

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ErrorResponse handleNotValid(MethodArgumentNotValidException ex) {
		final FieldError firstFieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().get();
		return new ErrorResponse(firstFieldError.getField(), firstFieldError.getDefaultMessage());
	}
}
