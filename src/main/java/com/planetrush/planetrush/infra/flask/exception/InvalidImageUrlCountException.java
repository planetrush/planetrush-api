package com.planetrush.planetrush.infra.flask.exception;

public class InvalidImageUrlCountException extends RuntimeException {

	public InvalidImageUrlCountException() {
	}

	public InvalidImageUrlCountException(String message) {
		super(message);
	}

	public InvalidImageUrlCountException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidImageUrlCountException(Throwable cause) {
		super(cause);
	}

	public InvalidImageUrlCountException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
