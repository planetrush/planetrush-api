package com.planetrush.planetrush.planet.exception;

public class DuplicatedDeleteResidentRequestException extends RuntimeException {

	public DuplicatedDeleteResidentRequestException() {
	}

	public DuplicatedDeleteResidentRequestException(String message) {
		super(message);
	}

	public DuplicatedDeleteResidentRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedDeleteResidentRequestException(Throwable cause) {
		super(cause);
	}
}
