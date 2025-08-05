package com.planetrush.planetrush.planet.exception;

/**
 * 챌린지가 시작한 후에 행성을 떠나려는 요청이 들어왔을 때 발생하는 예외입니다.
 */
public class ResidentExitTimeoutException extends RuntimeException {

	public ResidentExitTimeoutException() {
	}

	public ResidentExitTimeoutException(Throwable cause) {
		super(cause);
	}

	public ResidentExitTimeoutException(String message) {
		super(message);
	}

	public ResidentExitTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResidentExitTimeoutException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
