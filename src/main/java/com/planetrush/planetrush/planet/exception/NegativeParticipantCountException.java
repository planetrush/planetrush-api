package com.planetrush.planetrush.planet.exception;

public class NegativeParticipantCountException extends RuntimeException {

	public NegativeParticipantCountException() {
	}

	public NegativeParticipantCountException(String message) {
		super(message);
	}

	public NegativeParticipantCountException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegativeParticipantCountException(Throwable cause) {
		super(cause);
	}

	public NegativeParticipantCountException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
