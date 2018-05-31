package com.libedi.demo.framework.handler;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RestControllerAdvice(annotations = RestController.class)
@Order(1)
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
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ErrorResponse handleBadRequest(IllegalArgumentException ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Bad Request");
	}
	
	/**
	 * 404 (NOT_FOUND)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ ResourceNotFoundException.class, FileNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ErrorResponse handleNotFound(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Not Found");
	}
	
	/**
	 * 409 (CONFLICT)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ResourceConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	protected ErrorResponse handleConflict(ResourceConflictException ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Conflict");
	}
	
	/**
	 * 500 (I/O Error)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleDataAccessError(DataAccessException ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error");
	}
	
	/**
	 * 500 (HTTP Client/Server Error)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ HttpClientErrorException.class, HttpServerErrorException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleHttpError(HttpStatusCodeException ex) {
		logger.error("HTTP ERROR STATUS : {} - {}", ex.getStatusCode(), ex.getStatusText());
		logger.error("HTTP ERROR BODY : {}", ex.getResponseBodyAsString(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getStatusText()) ? ex.getStatusText() : "Internal Server Error");
	}
	
	/**
	 * 500 (Rest Client Error)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RestClientException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleRestClientError(RestClientException ex) {
		logger.error("REST CLIENT ERROR : {}", ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error");
	}
	
	/**
	 * 500 (INTERNAL_SERVER_ERROR)
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleInternalServerError(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorResponse(StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : "Internal Server Error");
	}

}
