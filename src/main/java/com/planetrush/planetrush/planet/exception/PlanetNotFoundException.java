package com.planetrush.planetrush.planet.exception;

public class PlanetNotFoundException extends RuntimeException {

	public PlanetNotFoundException() {
	}

	public PlanetNotFoundException(String message) {
		super(message);
	}

	public PlanetNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlanetNotFoundException(Throwable cause) {
		super(cause);
	}

	public PlanetNotFoundException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
