package com.planetrush.planetrush.planet.exception;

public class ResidentAlreadyExistsException extends RuntimeException {

	public ResidentAlreadyExistsException() {
	}

	public ResidentAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public ResidentAlreadyExistsException(String message) {
		super(message);
	}

	public ResidentAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResidentAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
