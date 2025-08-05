package com.planetrush.planetrush.infra.flask.exception;

public class FlaskServerNotConnectedException extends RuntimeException {

	public FlaskServerNotConnectedException() {
	}

	public FlaskServerNotConnectedException(String message) {
		super(message);
	}

	public FlaskServerNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlaskServerNotConnectedException(Throwable cause) {
		super(cause);
	}

	protected FlaskServerNotConnectedException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
