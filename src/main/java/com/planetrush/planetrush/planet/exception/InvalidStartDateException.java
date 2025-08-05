package com.planetrush.planetrush.planet.exception;

public class InvalidStartDateException extends RuntimeException {

	public InvalidStartDateException() {
	}

	public InvalidStartDateException(String message) {
		super(message);
	}

	public InvalidStartDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidStartDateException(Throwable cause) {
		super(cause);
	}

	protected InvalidStartDateException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
