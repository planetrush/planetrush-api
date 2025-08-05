package com.planetrush.planetrush.planet.exception;

public class ResidentOverflowException extends RuntimeException {

	public ResidentOverflowException() {
	}

	public ResidentOverflowException(String message) {
		super(message);
	}

	public ResidentOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResidentOverflowException(Throwable cause) {
		super(cause);
	}

	protected ResidentOverflowException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
