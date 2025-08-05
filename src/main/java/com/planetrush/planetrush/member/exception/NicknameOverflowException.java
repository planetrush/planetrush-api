package com.planetrush.planetrush.member.exception;

public class NicknameOverflowException extends RuntimeException{

	public NicknameOverflowException() {
	}

	public NicknameOverflowException(String message) {
		super(message);
	}

	public NicknameOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public NicknameOverflowException(Throwable cause) {
		super(cause);
	}

	public NicknameOverflowException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
