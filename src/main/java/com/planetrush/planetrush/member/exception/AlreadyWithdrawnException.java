package com.planetrush.planetrush.member.exception;

public class AlreadyWithdrawnException extends RuntimeException {

	public AlreadyWithdrawnException() {
	}

	public AlreadyWithdrawnException(String message) {
		super(message);
	}

	public AlreadyWithdrawnException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyWithdrawnException(Throwable cause) {
		super(cause);
	}

	protected AlreadyWithdrawnException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}