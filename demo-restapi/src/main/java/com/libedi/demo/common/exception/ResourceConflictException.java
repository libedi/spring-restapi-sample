package com.libedi.demo.common.exception;

/**
 * ResourceConflictException
 * @author Sangjun, Park
 *
 */
public class ResourceConflictException extends RuntimeException {
	
	private static final long serialVersionUID = -1633639347587897831L;

	public ResourceConflictException() {
		super();
	}

	public ResourceConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResourceConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceConflictException(String message) {
		super(message);
	}

	public ResourceConflictException(Throwable cause) {
		super(cause);
	}

}
