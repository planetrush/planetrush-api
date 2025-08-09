package com.planetrush.planetrush.infra.flask.exception;

public class FlaskConnectionFailedException extends RuntimeException {

	public FlaskConnectionFailedException() {
	}

	public FlaskConnectionFailedException(String message) {
		super(message);
	}

	public FlaskConnectionFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlaskConnectionFailedException(Throwable cause) {
		super(cause);
	}

	protected FlaskConnectionFailedException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
