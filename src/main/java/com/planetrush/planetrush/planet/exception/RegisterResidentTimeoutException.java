package com.planetrush.planetrush.planet.exception;

/**
 * 챌린지 시작 일자 보다 후에 입주 신청을 하였을 때 발생하는 예외 입니다.
 */
public class RegisterResidentTimeoutException extends RuntimeException {

	public RegisterResidentTimeoutException() {
	}

	public RegisterResidentTimeoutException(Throwable cause) {
		super(cause);
	}

	public RegisterResidentTimeoutException(String message) {
		super(message);
	}

	public RegisterResidentTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterResidentTimeoutException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
