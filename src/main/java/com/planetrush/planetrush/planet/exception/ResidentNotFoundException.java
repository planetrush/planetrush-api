package com.planetrush.planetrush.planet.exception;

public class ResidentNotFoundException extends RuntimeException {

	public ResidentNotFoundException() {
	}

	public ResidentNotFoundException(Throwable cause) {
		super(cause);
	}

	public ResidentNotFoundException(String message) {
		super(message);
	}

	public ResidentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResidentNotFoundException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
