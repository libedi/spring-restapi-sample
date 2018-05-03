package com.libedi.demo.framework.handler;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.libedi.demo.common.exception.ResourceConflictException;
import com.libedi.demo.common.model.ErrorResponse;

/**
 * RestResponseEntityExceptionHandler
 * @author Sangjun, Park
 *
 */
/*
 * ResponseEntityExceptionHandler를 상속받지 않고, @RestControllerAdvice를 사용할 수도 있다.
 * 이때는 반환값이 ResponseEntity<Object>가 아니라, ErrorResponse 객체를 반환하면 된다.
 */
//@RestControllerAdvice
@ControllerAdvice(annotations = RestController.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	/**
	 * 400 (BAD_REQUEST)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		final FieldError firstFieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().get();
		return super.handleExceptionInternal(ex,
				new ErrorResponse(firstFieldError.getField(), firstFieldError.getDefaultMessage()), headers,
				HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * 400 (BAD_REQUEST)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Bad Request"),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * 404 (NOT_FOUND)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler({ ResourceNotFoundException.class, FileNotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Not Found"),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * 409 (Conflict)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(ResourceConflictException.class)
	protected ResponseEntity<Object> handleConflict(ResourceConflictException ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Conflict"),
				new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	/**
	 * 500 (I/O Error)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataAccessError(DataAccessException ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error"),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	/**
	 * 500 (HTTP Client/Server Error)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ HttpClientErrorException.class, HttpServerErrorException.class })
	protected ResponseEntity<Object> handleHttpError(HttpStatusCodeException ex, WebRequest request) {
		logger.error("HTTP ERROR STATUS : {} - {}", ex.getStatusCode(), ex.getStatusText());
		logger.error("HTTP ERROR BODY : {}", ex.getResponseBodyAsString(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(
						StringUtils.hasLength(ex.getStatusText()) ? ex.getStatusText() : "Internal Server Error"),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	/**
	 * 500 (Rest Client Error)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return
	 */
	@ExceptionHandler(RestClientException.class)
	protected ResponseEntity<Object> handleRestClientError(RestClientException ex, WebRequest request) {
		logger.error("REST CLIENT ERROR : {}", ex.getMessage(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error"),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	/**
	 * 500 (INTERNAL_SERVER_ERROR)
	 * @param ex
	 * @param headers
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return super.handleExceptionInternal(ex,
				new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error"),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
