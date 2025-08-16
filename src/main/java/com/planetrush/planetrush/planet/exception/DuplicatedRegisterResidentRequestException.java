package com.planetrush.planetrush.planet.exception;

public class DuplicatedRegisterResidentRequestException extends RuntimeException {

	public DuplicatedRegisterResidentRequestException() {
	}

	public DuplicatedRegisterResidentRequestException(String message) {
		super(message);
	}

	public DuplicatedRegisterResidentRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedRegisterResidentRequestException(Throwable cause) {
		super(cause);
	}
}
