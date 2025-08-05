package com.planetrush.planetrush.infra.flask.exception;

public class ProgressAvgNotFoundException extends RuntimeException {

	public ProgressAvgNotFoundException() {
	}

	public ProgressAvgNotFoundException(String message) {
		super(message);
	}

	public ProgressAvgNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProgressAvgNotFoundException(Throwable cause) {
		super(cause);
	}

	protected ProgressAvgNotFoundException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
