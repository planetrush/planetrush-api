package com.planetrush.planetrush.planet.exception;

public class ParticipantsOverflowException extends RuntimeException {

	public ParticipantsOverflowException() {
	}

	public ParticipantsOverflowException(String message) {
		super(message);
	}

	public ParticipantsOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParticipantsOverflowException(Throwable cause) {
		super(cause);
	}

	public ParticipantsOverflowException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
